package com.manujainz.kotlyze.visitors

import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*

/**
 * Visits loops and checks for inefficient operations such as string concatenations and object instantiations.
 *
 * Examples:
 *  for(i in 1..10) {
 *      var str = "Hello" + "World" // Inefficient string concatenation
 *      var obj = MyClass()         // Object instantiation
 *  }
 */
class InefficientOperationsWhileLoopingVisitor : KotlyzeVisitor() {

    override fun visitLoopExpression(loopExpression: KtLoopExpression) {
        super.visitLoopExpression(loopExpression)

        // Visit the loop expression when detected
        loopExpression.body?.accept(object : KotlyzeVisitor() {

            override fun visitBinaryExpression(expression: KtBinaryExpression) {
                super.visitBinaryExpression(expression)
                if (expression.operationToken == KtTokens.PLUS && (expression.left is KtStringTemplateExpression || expression.right is KtStringTemplateExpression)) {
                    recordIssue(getLineNumber(expression), "Inefficient string concatenation detected inside a loop.")
                }
            }

            override fun visitCallExpression(expression: KtCallExpression) {
                super.visitCallExpression(expression)
                if (expression.parent is KtProperty) {
                    recordIssue(getLineNumber(expression), "Object instantiation detected inside a loop.")
                }
            }
        })
    }
}
