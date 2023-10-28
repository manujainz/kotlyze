package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType

/**
 * Detects functions with more than the allowed number of return statements.
 */
class ReturnStatementVisitor(private val maxAllowedReturns: Int) : KotlyzeVisitor() {

    override fun visitNamedFunction(function: KtNamedFunction) {
        val returnStatements = function.collectDescendantsOfType<KtReturnExpression>()

        if (returnStatements.size > maxAllowedReturns) {
            recordIssue(
                getLineNumber(function),
                "Function '${function.name}' has more than $maxAllowedReturns return statements."
            )
        }

        super.visitNamedFunction(function)
    }
}
