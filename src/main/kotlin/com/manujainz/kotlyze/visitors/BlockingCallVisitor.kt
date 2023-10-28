package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.*
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.lexer.KtTokens

class BlockingCallInCoroutineVisitor : KotlyzeVisitor() {

    private var insideCoroutineContext = false

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)

        when {
            insideCoroutineContext && expression.calleeExpression?.text == "sleep" && expression.parent.text.startsWith("Thread") -> {
                recordIssue(getLineNumber(expression), "Blocking call `Thread.sleep()` inside coroutine.")
            }

            insideCoroutineContext && expression.calleeExpression?.text == "runBlocking" -> {
                recordIssue(getLineNumber(expression), "Usage of `runBlocking` inside coroutine.")
            }

            !insideCoroutineContext && expression.calleeExpression?.text == "delay" -> {
                recordIssue(getLineNumber(expression), "`delay` should be used inside coroutine or suspend function.")
            }
        }
    }

    override fun visitNamedFunction(function: KtNamedFunction) {
        val wasInsideCoroutineContext = insideCoroutineContext
        if (function.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
            insideCoroutineContext = true
        }

        super.visitNamedFunction(function)

        insideCoroutineContext = wasInsideCoroutineContext
    }
}
