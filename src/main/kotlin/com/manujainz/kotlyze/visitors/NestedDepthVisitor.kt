package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

/**
 * Detects code blocks with excessive nesting.
 */
class NestedDepthVisitor(private val maxNestingDepth: Int) : KotlyzeVisitor() {

    private var currentDepth = 0

    private fun <T: KtElement> visitNested(element: T, visit: () -> Unit) {
        currentDepth++
        if (currentDepth > maxNestingDepth) {
            recordIssue(
                getLineNumber(element),
                "Excessive nesting detected. Current depth is $currentDepth while max allowed is $maxNestingDepth.")
        }
        visit.invoke()
        currentDepth--
    }

    override fun visitIfExpression(expression: KtIfExpression) {
        visitNested(expression) {
            super.visitIfExpression(expression)
        }
    }

    override fun visitForExpression(expression: KtForExpression) {
        visitNested(expression) {
            super.visitForExpression(expression)
        }
    }

    override fun visitWhileExpression(expression: KtWhileExpression) {
        visitNested(expression) {
            super.visitWhileExpression(expression)
        }
    }

    override fun visitWhenExpression(expression: KtWhenExpression) {
        visitNested(expression) {
            super.visitWhenExpression(expression)
        }
    }
}
