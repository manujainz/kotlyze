package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

private const val SCOPE_GLOBAL = "GlobalScope"

class CoroutineGlobalScopeUsageVisitor : KotlyzeVisitor() {

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)

        val receiver = expression.receiverExpression
        val selector = expression.selectorExpression

        if (receiver.text == SCOPE_GLOBAL && selector is KtCallExpression) {
            val calledFunctionName = selector.calleeExpression?.text
            if (calledFunctionName in setOf("launch", "async")) {
                recordIssue(
                    getLineNumber(expression),
                    "Usage of $calledFunctionName in GlobalScope detected. Avoid using GlobalScope directly."
                )
            }
        }
    }
}
