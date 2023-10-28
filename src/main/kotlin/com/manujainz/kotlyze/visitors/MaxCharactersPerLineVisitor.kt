package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class MaxCharactersPerLineVisitor(private val maxCharAllowedPerLine: Int): KotlyzeVisitor() {

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)

        val lines = file.text.split("\n")

        for ((index, line) in lines.withIndex()) {
            if (line.length > maxCharAllowedPerLine) {
                recordIssue(
                    index + 1,
                    "Line ${index + 1} exceeds the max limit of $maxCharAllowedPerLine characters."
                )
            }
        }
    }
}