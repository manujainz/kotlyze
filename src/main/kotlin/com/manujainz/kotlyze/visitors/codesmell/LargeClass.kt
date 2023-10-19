package com.manujainz.kotlyze.visitors.codesmell

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.allConstructors
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

private const val MAX_METHOD_COUNT_ALLOWED = 0
private const val MAX_CONSTRUCTOR_PARAM_ALLOWED = 0

class GodClassCheck: KotlyzeVisitor() {

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        checkMethodCount(klass)
        checkConstructorParams(klass)
    }

    private fun checkConstructorParams(klass: KtClass) {
        klass.allConstructors.forEach {
            if (it.typeParameters.size > MAX_CONSTRUCTOR_PARAM_ALLOWED) {
                println("Class ${klass.name} constructor ${it.name} exceeds " +
                        "the max limit of $MAX_CONSTRUCTOR_PARAM_ALLOWED constructors.")

            }
        }
    }

    private fun checkMethodCount(klass: KtClass) {
        var methodCount = 0
        klass.declarations.forEach {
            if (it is KtFunction) {
                if (++methodCount > MAX_METHOD_COUNT_ALLOWED) {
                    println("Class ${klass.name} exceeds the max limit of $MAX_METHOD_COUNT_ALLOWED methods.")
                    return
                }
            }
        }
    }
}