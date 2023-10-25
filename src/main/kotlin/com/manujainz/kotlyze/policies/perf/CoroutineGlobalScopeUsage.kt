package com.manujainz.kotlyze.policies.perf

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.CoroutineGlobalScopeUsageVisitor
import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport

class CoroutineGlobalScopeUsage(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.PERFORMANCE

    override val description =
        "Usage of GlobalScope is discouraged as it " +
            "might lead to unforeseen issues. " +
            "Prefer using a properly scoped coroutine."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = CoroutineGlobalScopeUsageVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}