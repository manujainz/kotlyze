package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.reporting.model.Issue
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class CoroutineGlobalScopeUsageVisitor : KotlyzeVisitor() {

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)

        val receiver = expression.receiverExpression
        val selector = expression.selectorExpression

        if (receiver.text == "GlobalScope" && selector is KtCallExpression) {
            val calledFunctionName = selector.calleeExpression?.text
            if (calledFunctionName in setOf("launch", "async")) {
                Issue(
                    fileName,
                    getLineNumber(expression),
                    "Usage of $calledFunctionName in GlobalScope detected. Avoid using GlobalScope directly."
                ).also {
                    detectedIssues.add(it)
                }
            }
        }
    }
}
