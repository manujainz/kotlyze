package com.manujainz.kotlyze.policies.base

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.model.PolicyConfig
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.Issue
import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import com.manujainz.kotlyze.visitors.base.KotlyzeVisitor

abstract class Policy constructor(val configLoader: ConfigLoader) {

    abstract val policyId: String

    abstract val issueType: IssueType

    abstract val description: String

    abstract val config: PolicyConfig

    abstract fun check(ktFiles: List<KtFile>)

    fun getDefaultPolicyConfig() = PolicyConfig(isEnabled = false, mapOf())

}

fun Policy.visitFilesAndReport(
    ktFiles: List<KtFile>,
    visitor: KotlyzeVisitor,
    reportEngine: ReportEngine
) {
    ktFiles.forEach {
        it.accept(visitor)

        val issues = visitor.detectedIssues
        issues.forEach { issue ->
            reportEngine.report(PolicyViolation(issue, issueType, policyId))
        }
    }
}