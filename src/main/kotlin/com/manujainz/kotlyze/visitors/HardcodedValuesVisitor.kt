package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

/**
 * Detects magic numbers in the code.
 */
class MagicNumbersVisitor : KotlyzeVisitor() {

    private val allowedMagicNumbers = setOf(-1, 0, 1)

    override fun visitConstantExpression(expression: KtConstantExpression) {
        super.visitConstantExpression(expression)
        if (expression.node.elementType.toString() == "INTEGER_CONSTANT" && expression.text.toIntOrNull() !in allowedMagicNumbers) {
            recordIssue(getLineNumber(expression), "Magic number detected: ${expression.text}. Consider using named constants.")
        }
    }
}
