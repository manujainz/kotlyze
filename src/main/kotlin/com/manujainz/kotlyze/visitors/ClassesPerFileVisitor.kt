package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

/**
 * Detects files with more than one top-level class or object.
 */
class MultipleClassesPerFileVisitor : KotlyzeVisitor() {

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)

        val topLevelClassOrObjects = file.declarations.filterIsInstance<KtClassOrObject>()

        if (topLevelClassOrObjects.size > 1) {
            recordIssue(
                getLineNumber(file),
                "File contains multiple top-level classes or objects."
            )
        }
    }
}
