package com.manujainz.kotlyze.policies.perf

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.InefficientCollectionOperationVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces efficient collection operations.
 *
 * Example:
 * Valid: val list = listOf(1, 2, 3)
 * Invalid: val list = listOf(1, 2, 3).toList()
 */
class InefficientCollectionOperation(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.PERFORMANCE

    override val description = "Enforces efficient collection operations."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = InefficientCollectionOperationVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
