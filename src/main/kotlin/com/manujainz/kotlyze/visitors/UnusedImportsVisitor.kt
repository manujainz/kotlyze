package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.idea.references.mainReference

/**
 * Detects unused import statements.
 */
class UnusedImportsVisitor : KotlyzeVisitor() {

    override fun visitImportList(importList: KtImportList) {
        super.visitImportList(importList)

        importList.imports.forEach { importDirective ->
            val reference = importDirective.importedReference
            val element = reference?.mainReference?.resolve()

            if (element == null) {
                recordIssue(
                    getLineNumber(importDirective),
                    "Unused import detected: ${importDirective.importedFqName}"
                )
            }
        }
    }
}
