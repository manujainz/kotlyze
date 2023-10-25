package com.manujainz.kotlyze.utils

import java.io.File

object FileUtils {

    fun readFile(path: String): String? {
        return try {
            File(path).readText(Charsets.UTF_8)
        } catch (e: Exception) {
            println("Exception when reading file from path $path")
            e.printStackTrace()
            null
        }
    }

    fun readFileFromRes(file: String): String? {
        var content: String? = null
        try {
            val resourceStream = this::class.java.classLoader.getResourceAsStream(file)
            resourceStream?.let {
                content = it.reader(Charsets.UTF_8).readText()
            }
        } catch (e: Exception) {
            println("Exception when reading file $file from resource stream")
            e.printStackTrace()
        }

        return content
    }
}