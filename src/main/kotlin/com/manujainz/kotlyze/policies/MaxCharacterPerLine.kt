package com.manujainz.kotlyze.policies

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolationModel
import visitors.codesmell.MaxCharactersPerLineVisitor

private const val DEFAULT_MAX_CHAR_PER_LINE = 30
private const val KEY_MAX_ALLOWED_CHAR_PER_LINE = "maxAllowedCharPerLine"

class MaxCharacterPerLine(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.canonicalName

    override val issueType = IssueType.CODE_SMELL

    override val description = "There should be a threshold on the number of characters per line."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxAllowedCharPerLine by PolicyConfigDelegate(
        this,
        KEY_MAX_ALLOWED_CHAR_PER_LINE,
        DEFAULT_MAX_CHAR_PER_LINE
    )

    override fun check(ktFiles: List<KtFile>) {
        val visitor = MaxCharactersPerLineVisitor(maxAllowedCharPerLine)
        ktFiles.forEach {
            it.accept(visitor)

            val issues = visitor.detectedIssues
            issues.forEach { issue ->
                reportEngine.report(PolicyViolationModel(issue, issueType, policyId))
            }
        }
    }

}