package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType

/**
 * Detects functions with direct recursive calls.
 */
class RecursiveCallVisitor : KotlyzeVisitor() {

    override fun visitNamedFunction(function: KtNamedFunction) {
        val functionName = function.name
        val callsInFunction = function.collectDescendantsOfType<KtCallExpression>()

        if (callsInFunction.any { it.calleeExpression?.text == functionName }) {
            recordIssue(
                getLineNumber(function),
                "Function '$functionName' has a direct recursive call."
            )
        }

        super.visitNamedFunction(function)
    }
}
