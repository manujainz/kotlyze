package com.manujainz.kotlyze.policies.complexity

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.ConditionChainVisitor
import org.jetbrains.kotlin.psi.KtFile

private const val DEFAULT_MAX_CONDITION = 0
private const val KEY_MAX_CONDITION = "maxAllowedConditionChain"

class ComplexConditionChain(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.COMPLEXITY

    override val description = "Large condition chains should be avoided to maintain low complexity"

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxAllowedConditions by PolicyConfigDelegate(this, KEY_MAX_CONDITION, DEFAULT_MAX_CONDITION)

    override fun check(ktFiles: List<KtFile>) {
        val visitor = ConditionChainVisitor(maxAllowedConditions)
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }

}