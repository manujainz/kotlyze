package com.manujainz.kotlyze.parser

import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.io.File

class KtParser(private val project: Project) {

    fun createKtFileFromPath(absolutePath: String, fileName: String): KtFile {
        val code = File(absolutePath).readText(Charsets.UTF_8)
        return createKtFile(code, fileName)
    }

    private fun createKtFile(code: String, fileName: String): KtFile {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        return psiFileFactory.createFileFromText(
            fileName,
            KotlinLanguage.INSTANCE,
            code
        ) as KtFile
    }
}
