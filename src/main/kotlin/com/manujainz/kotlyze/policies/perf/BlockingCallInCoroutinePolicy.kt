package com.manujainz.kotlyze.policies.perf

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.BlockingCallInCoroutineVisitor
import org.jetbrains.kotlin.psi.KtFile

class BlockingCallInCoroutine(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.PERFORMANCE

    override val description = "Blocking calls inside coroutines should be avoided to prevent performance bottlenecks."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = BlockingCallInCoroutineVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
