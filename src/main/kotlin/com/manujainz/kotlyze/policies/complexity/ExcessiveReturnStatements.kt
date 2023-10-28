package com.manujainz.kotlyze.policies.complexity

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.visitors.ReturnStatementVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces a limit on the number of return statements in functions.
 *
 * Example:
 *  fun complexFunction(): Int {
 *      if (condition1) return 1
 *      if (condition2) return 2
 *      // ...
 *      return 3
 *  } // This function has multiple return statements
 */

private const val KEY_MAX_ALLOWED_RETURNS = "maxAllowedReturns"
private const val DEFAULT_MAX_ALLOWED_RETURNS = 3

class ExcessiveReturnStatements(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.COMPLEXITY

    override val description = "Warns about functions with multiple return statements, which can make them harder to understand."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxAllowedReturns by PolicyConfigDelegate(this, KEY_MAX_ALLOWED_RETURNS, DEFAULT_MAX_ALLOWED_RETURNS)

    override fun check(ktFiles: List<KtFile>) {
        val visitor = ReturnStatementVisitor(maxAllowedReturns)
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
