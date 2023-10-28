package com.manujainz.kotlyze.visitors

import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Visits and checks for potential misuse or inefficient handling of Bitmaps.
 *
 * Detects:
 * - Direct use of BitmapFactory. Decode* methods without resizing options.
 */
class BitmapVisitor : KotlyzeVisitor() {

    private val bitmapFactoryMethods = setOf("decodeFile", "decodeResource", "decodeStream", "decodeByteArray", "decodeFileDescriptor")

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        val importedName = importDirective.importedFqName?.asString()

        if (importedName != null && "android.graphics.BitmapFactory" in importedName) {
            val method = importedName.split(".").lastOrNull()
            if (method in bitmapFactoryMethods) {
                recordIssue(getLineNumber(importDirective), "Potential inefficient Bitmap loading via $method without resizing options.")
            }
        }
    }

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        val callee = expression.calleeExpression

        // Checking for direct BitmapFactory usage without options
        if (callee != null && callee.text in bitmapFactoryMethods) {
            recordIssue(getLineNumber(expression), "Direct use of BitmapFactory's ${callee.text} without specifying options to limit the size.")
        }
    }
}
