package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.Issue
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import com.manujainz.kotlyze.visitors.CodeLineVisitor
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitationListener
import org.jetbrains.kotlin.psi.KtFile

private const val DEFAULT_MAX_CHAR_PER_LINE = 120
private const val KEY_MAX_ALLOWED_CHAR_PER_LINE = "maxAllowedCharPerLine"

class MaxCharacterPerLine(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "There should be a threshold on the number of characters per line."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxAllowedCharPerLine by PolicyConfigDelegate(
        this,
        KEY_MAX_ALLOWED_CHAR_PER_LINE,
        DEFAULT_MAX_CHAR_PER_LINE
    )

    override fun check(ktFiles: List<KtFile>) {

        val issues = mutableListOf<Issue>()

        val visitor = CodeLineVisitor(object : KotlyzeVisitationListener {
            override fun onLineVisit(content: String, lineNo: Int, fileName: String) {
                if (content.length > maxAllowedCharPerLine) {
                    issues.add(
                        Issue(
                            fileName,
                            lineNo,
                            "Line ${lineNo + 1} exceeds the max limit of $maxAllowedCharPerLine characters."
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