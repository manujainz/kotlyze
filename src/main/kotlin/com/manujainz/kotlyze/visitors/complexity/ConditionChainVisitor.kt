package com.manujainz.kotlyze.visitors.complexity

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.reporting.model.Issue
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class ConditionChainVisitor(private val allowedConditionsPerChain: Int): KotlyzeVisitor() {

    override fun visitIfExpression(expression: KtIfExpression) {
        super.visitIfExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            Issue(
                fileName,
                getLineNumber(expression),
                "If condition chain exceeding threshold of $allowedConditionsPerChain"
            ).also {
                detectedIssues.add(it)
            }
        }
    }

    override fun visitWhileExpression(expression: KtWhileExpression) {
        super.visitWhileExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            Issue(
                fileName,
                getLineNumber(expression),
                "While condition chain exceeding threshold of $allowedConditionsPerChain"
            ).also {
                detectedIssues.add(it)
            }
        }
    }

    override fun visitDoWhileExpression(expression: KtDoWhileExpression) {
        super.visitDoWhileExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            Issue(
                fileName,
                getLineNumber(expression),
                "Do while condition chain exceeding threshold of $allowedConditionsPerChain"
            ).also {
                detectedIssues.add(it)
            }
        }
    }

    private fun getConditionCount(expression: KtExpression?): Int {
        if (expression == null) return 0

        return when (expression) {
            is KtSimpleNameExpression, is KtConstantExpression -> 1
            is KtBinaryExpression -> {
                val op = expression.operationToken
                if (op == KtTokens.ANDAND || op == KtTokens.OROR) {
                    1 + getConditionCount(expression.left) + getConditionCount(expression.right)
                } else {
                    getConditionCount(expression.left) + getConditionCount(expression.right)
                }
            }
            else -> 0
        }
    }


}
