package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

/**
 * Detects functions with high cyclomatic complexity.
 */
class CyclomaticComplexityVisitor(private val maxComplexity: Int) : KotlyzeVisitor() {

    private var complexityCounter = 0

    override fun visitNamedFunction(function: KtNamedFunction) {
        complexityCounter = 1 // default
        super.visitNamedFunction(function)

        if (complexityCounter > maxComplexity) {
            recordIssue(
                getLineNumber(function),
                "Function '${function.name}' has a cyclomatic complexity of $complexityCounter, " +
                        "which exceeds the max allowed value of $maxComplexity.")
        }
    }

    override fun visitIfExpression(expression: KtIfExpression) {
        complexityCounter++
        super.visitIfExpression(expression)
    }

    override fun visitWhenExpression(expression: KtWhenExpression) {
        complexityCounter++
        super.visitWhenExpression(expression)
    }

    override fun visitForExpression(expression: KtForExpression) {
        complexityCounter++
        super.visitForExpression(expression)
    }

    override fun visitWhileExpression(expression: KtWhileExpression) {
        complexityCounter++
        super.visitWhileExpression(expression)
    }

    override fun visitDoWhileExpression(expression: KtDoWhileExpression) {
        complexityCounter++
        super.visitDoWhileExpression(expression)
    }
}
