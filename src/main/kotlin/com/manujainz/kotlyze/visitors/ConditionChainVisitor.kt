package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class ConditionChainVisitor(private val allowedConditionsPerChain: Int): KotlyzeVisitor() {

    override fun visitIfExpression(expression: KtIfExpression) {
        super.visitIfExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            recordIssue(getLineNumber(expression), "If condition chain exceeding threshold of $allowedConditionsPerChain")
        }
    }

    override fun visitWhileExpression(expression: KtWhileExpression) {
        super.visitWhileExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            recordIssue(getLineNumber(expression),"While condition chain exceeding threshold of $allowedConditionsPerChain")
        }
    }

    override fun visitDoWhileExpression(expression: KtDoWhileExpression) {
        super.visitDoWhileExpression(expression)
        val conditionCount = getConditionCount(expression.condition)
        if (conditionCount > allowedConditionsPerChain) {
            recordIssue(getLineNumber(expression),"Do-While condition chain exceeding threshold of $allowedConditionsPerChain")
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
