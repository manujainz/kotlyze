package com.manujainz.kotlyze.plugin.init

import com.manujainz.kotlyze.config.JsonConfigLoader
import com.manujainz.kotlyze.parser.KotlyzeParser
import com.manujainz.kotlyze.plugin.KotlyzeExtension
import com.manujainz.kotlyze.policies.base.factory.PolicyFactory
import com.manujainz.kotlyze.reporting.core.ReportEngineImpl
import com.manujainz.kotlyze.reporting.publishers.SystemLogReportPublisher
import com.manujainz.kotlyze.utils.FileUtils
import org.gradle.api.Project
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.messages.MessageCollector.Companion.NONE
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

private const val DEFAULT_KOTLYZE_CONFIG = "kotlyze_config.json"

class KotlyzeStartup {

    private var compileConfiguration = CompilerConfiguration().apply {
        put(MESSAGE_COLLECTOR_KEY, NONE)
    }

    private var project =
        KotlinCoreEnvironment
            .createForProduction({}, compileConfiguration, JVM_CONFIG_FILES).project

    fun start(target: Project, extension: KotlyzeExtension) {
        val kotlyzeParser = KotlyzeParser(project)

        // setup dependencies
        val config = getKotlyzeConfig(extension.configPath)
        val configLoader = JsonConfigLoader(config)

        println(config)

        configLoader.printConfig()

        val reportingEngine = ReportEngineImpl()
        val publisher = SystemLogReportPublisher()

        // parse kotlin files
        val ktFiles = getParsedKtFiles(target, extension, kotlyzeParser)

        // fetch policies
        val policies = PolicyFactory(configLoader, reportingEngine).getAllPolicies()
        println("policy count: ${policies.size}")

        // check kotlin files for policy violation
        policies.forEach {
            println(it)
            it.check(ktFiles)
        }

        // report and publish
        reportingEngine.notifyReporters(listOf(publisher))
    }

    private fun getKotlyzeConfig(configPath: String?): String {
        return if (configPath != null) {
            FileUtils.readFile(configPath)?.takeIf { it.isNotBlank() } ?: getDefaultKotlyzeConfig() ?: ""
        } else {
            getDefaultKotlyzeConfig() ?: ""
        }
    }

    private fun getDefaultKotlyzeConfig() = FileUtils.readFileFromRes(DEFAULT_KOTLYZE_CONFIG)

    private fun getParsedKtFiles(
        targetProject: Project,
        extension: KotlyzeExtension,
        kotlyzeParser: KotlyzeParser
    ): List<KtFile> {
        val ktFiles = mutableListOf<KtFile>()
        getKotlinFiles(targetProject, extension).forEach {
            ktFiles.add(kotlyzeParser.createKtFileFromPath(
                it.absolutePath,
                it.name
            ))
        }
        return ktFiles
    }

    private fun getKotlinFiles(targetProject: Project, extension: KotlyzeExtension): List<File> {
        val resolvedTargetPath = File(targetProject.rootDir, extension.targetPath.toString())
        return getKotlinFilesFromPath(resolvedTargetPath.absolutePath)
    }

    private fun getKotlinFilesFromPath(directoryPath: String): List<File> {
        val root = File(directoryPath)
        return root.walkTopDown().filter { it.extension == "kt" }.toList()
    }

}