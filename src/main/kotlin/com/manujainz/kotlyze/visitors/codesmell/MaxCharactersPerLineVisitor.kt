package visitors.codesmell

import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.reporting.model.Issue
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

class MaxCharactersPerLineVisitor(private val maxCharAllowedPerLine: Int): KotlyzeVisitor() {

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)

        val lines = file.text.split("\n")

        for ((index, line) in lines.withIndex()) {
            if (line.length > maxCharAllowedPerLine) {
                val issue = Issue(
                    fileName = file.name,
                    lineNo = index + 1,
                    msg = "Line ${index + 1} exceeds the max limit of $maxCharAllowedPerLine characters."
                )
                policyViolationListener?.onPolicyViolated(issue)
                detectedIssues.add(issue)
            }
        }
    }
}