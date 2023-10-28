package com.manujainz.kotlyze.visitors.base

import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid
import com.manujainz.kotlyze.reporting.model.Issue
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

abstract class KotlyzeVisitor: KtTreeVisitorVoid() {

    val detectedIssues = mutableListOf<Issue>()

    var policyViolationListener: PolicyViolationListener? = null

    protected lateinit var fileName: String

    private var fileDoc: Document? = null

    override fun visitKtFile(file: KtFile) {
        fileDoc = FileDocumentManager.getInstance().getDocument(file.virtualFile)
        fileName = file.name
        super.visitKtFile(file)
        println("KtFile Visited: ${file.name}")
    }

    protected fun getLineNumber(element: PsiElement): Int {
        return (fileDoc?.getLineNumber(element.textOffset)?.plus(1)) ?: -1
    }

    protected fun recordIssue(lineNo: Int, msg: String) {
        detectedIssues.add(Issue(fileName, lineNo, msg))
    }
}