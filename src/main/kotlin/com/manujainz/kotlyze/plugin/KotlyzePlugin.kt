package com.manujainz.kotlyze.plugin

import com.manujainz.kotlyze.plugin.init.KotlyzeStartup
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlyzePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create("kotlyze", KotlyzeExtension::class.java)

        target.afterEvaluate {
            val kotlyzeExtension = target.extensions.getByType(KotlyzeExtension::class.java)
            println("Kotlyze: Init ***************")

            println("Config path: ${kotlyzeExtension.configPath}")
            println("Kt src path: ${kotlyzeExtension.targetPath}")

            KotlyzeStartup().start(target, kotlyzeExtension)

        }
    }
}