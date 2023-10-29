package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.Issue
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import com.manujainz.kotlyze.visitors.CodeLineVisitor
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitationListener
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces no trailing whitespace at the end of lines.
 *
 * Example:
 *  val x = 10;<whitespace>  // Trailing whitespace detected.
 */
class TrailingWhitespace(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "Ensure there's no trailing whitespace at the end of lines."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {

        val issues = mutableListOf<Issue>()

        val visitor = CodeLineVisitor(object : KotlyzeVisitationListener {
            override fun onLineVisit(content: String, lineNo: Int, fileName: String) {
                if (content.endsWith(" ")) {
                    issues.add(
                        Issue(
                            fileName,
                            lineNo,
                            "Line $lineNo has trailing whitespace."
                        )
                    )
                }
            }
        })

        ktFiles.forEach {
            it.accept(visitor)
            issues.forEach { issue ->
                reportEngine.report(PolicyViolation(issue, issueType, policyId))
            }
        }
    }
}
