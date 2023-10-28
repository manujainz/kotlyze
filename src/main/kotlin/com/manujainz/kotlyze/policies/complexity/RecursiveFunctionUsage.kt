package com.manujainz.kotlyze.policies.complexity

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.visitors.RecursiveCallVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Detects direct recursive calls in functions.
 *
 * Example:
 *  fun recursiveFunction() {
 *      recursiveFunction()
 *  }
 */
class RecursiveFunctionUsage(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.COMPLEXITY

    override val description = "Detect and warn about direct recursive calls in functions."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = RecursiveCallVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}