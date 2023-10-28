package com.manujainz.kotlyze.visitors

import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtExpression

/**
 * Visit collections callee and checks inefficient operations
 *
 * Example:
 *  val set = setOf(1, 2, 3).toSet() // Inefficient operation
 */
class InefficientCollectionOperationVisitor : KotlyzeVisitor() {

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        val callee = expression.calleeExpression

        if (callee != null && isRedundantCollectionConversion(expression, callee.text)) {
            recordIssue(getLineNumber(expression), "Redundant collection operation '${callee.text}' detected.")
        }
    }

    private fun isRedundantCollectionConversion(expression: KtExpression, callee: String): Boolean {
        return when (callee) {
            "toList" -> expression.parent is KtCallExpression && (expression.parent as KtCallExpression).calleeExpression?.text == "listOf"
            "toSet" -> expression.parent is KtCallExpression && (expression.parent as KtCallExpression).calleeExpression?.text == "setOf"
            else -> false
        }
    }
}
