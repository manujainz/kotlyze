package com.manujainz.kotlyze.visitors

import com.manujainz.kotlyze.visitors.base.KotlyzeVisitationListener
import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class CodeLineVisitor(
    private val visitationListener: KotlyzeVisitationListener
    ): KotlyzeVisitor() {

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)

        val lines = file.text.split("\n")

        for ((index, line) in lines.withIndex()) {
            visitationListener.onLineVisit(
                line,
                index + 1,
                fileName
            )
        }
    }
}