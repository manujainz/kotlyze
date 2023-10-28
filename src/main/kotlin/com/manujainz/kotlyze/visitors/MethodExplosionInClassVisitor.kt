package com.manujainz.kotlyze.visitors

import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.allConstructors
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class ClassMethodVisitor(
    private val maxMethodCountPerClass: Int,
    private val maxConstructorParamsPerMethod: Int
): KotlyzeVisitor() {

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        checkMethodCount(klass)
        checkConstructorParams(klass)
    }

    private fun checkConstructorParams(klass: KtClass) {
        klass.allConstructors.forEach {
            if (it.typeParameters.size > maxConstructorParamsPerMethod) {
                recordIssue(
                    -1,
                    "Class ${klass.name} constructor ${it.name} exceeds " +
                            "the max limit of $maxConstructorParamsPerMethod constructors."
                )
            }
        }
    }

    private fun checkMethodCount(klass: KtClass) {
        var methodCount = 0
        klass.declarations.forEach {
            if (it is KtFunction) {
                if (++methodCount > maxMethodCountPerClass) {
                    recordIssue(
                        -1,
                        "Class ${klass.name} exceeds the max limit of $maxMethodCountPerClass methods."
                    )
                    return
                }
            }
        }
    }
}