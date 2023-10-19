package com.manujainz.kotlyze.plugin

import com.manujainz.kotlyze.config.JsonConfigLoader
import com.manujainz.kotlyze.parser.KtParser
import com.manujainz.kotlyze.policies.LargeConditionChain
import com.manujainz.kotlyze.policies.MaxCharacterPerLine
import com.manujainz.kotlyze.reporting.core.ReportEngineImpl
import com.manujainz.kotlyze.reporting.publishers.SystemLogReportPublisher
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

class KotlyzePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create("kotlyze", KotlyzeExtension::class.java)

        target.afterEvaluate {
            val kotlyzeExtension = target.extensions.getByType(KotlyzeExtension::class.java)
            println("kotlyze logs *******************")
            println(kotlyzeExtension.configPath)
            println(kotlyzeExtension.targetPath)


            val configuration = CompilerConfiguration().apply {
                put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
            }

            val environment = KotlinCoreEnvironment.createForProduction(
                {}, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES
            )

            val resolvedTargetPath = File(target.rootDir, kotlyzeExtension.targetPath.toString())
            val kotlinFiles = getKotlinFilesFromPackage(resolvedTargetPath.absolutePath)


            // Parse Files to AST (KtFile)
            val ktParser = KtParser(environment.project)

            val ktFiles = mutableListOf<KtFile>()

            kotlinFiles.forEach {
                ktFiles.add(ktParser.createKtFileFromPath(it.absolutePath, it.name))
            }

            // Dependencies
            val configLoader = JsonConfigLoader(getJsonConfig())
            val reportingEngine = ReportEngineImpl()

            val policies = listOf(
                MaxCharacterPerLine(configLoader, reportingEngine),
                LargeConditionChain(configLoader, reportingEngine)
            )

            policies.forEach {
                it.check(ktFiles)
            }

            val publisher = SystemLogReportPublisher()
            reportingEngine.notifyReporters(listOf(publisher))

        }
    }
}

private fun getJsonConfig() = "{\n" +
        "  \"TooManyClassMethods\": {\n" +
        "    \"enabled\": true,\n" +
        "    \"maxMethodsAllowed\": 10\n" +
        "  },\n" +
        "  \"LongVariableName\": {\n" +
        "    \"enabled\": false,\n" +
        "    \"maxNameLength\": 15\n" +
        "  },\n" +
        "  \"UnusedImport\": {\n" +
        "    \"enabled\": true\n" +
        "  }\n" +
        "}\n"

fun getKotlinFilesFromPackage(directoryPath: String): List<File> {
    val root = File(directoryPath)
    println("Checking directory: ${root.absolutePath}")
    return root.walkTopDown().filter { it.extension == "kt" }.toList()
}
