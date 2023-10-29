package com.manujainz.kotlyze.policies.complexity

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.visitors.CyclomaticComplexityVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces a maximum cyclomatic complexity for functions to ensure maintainability and testability.
 *
 * Example:
 *  fun example() {
 *      if (...) { ... }
 *      for (...) { ... }
 *      while (...) { ... }
 *  }
 *  // Cyclomatic complexity = 4 (1 by default + 3 for each decision point)
 */

private const val KEY_ALLOWED_MAX_COMPLEXITY_POINTS = "maxAllowedComplexityPath"
private const val DEFAULT_ALLOWED_MAX_COMPLEXITY_POINTS = 5

class HighCyclomaticComplexity(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.COMPLEXITY

    override val description = "Avoid high cyclomatic complexity to improve code maintainability and testability"

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxComplexityAllowed by PolicyConfigDelegate(
        this,
        KEY_ALLOWED_MAX_COMPLEXITY_POINTS,
        DEFAULT_ALLOWED_MAX_COMPLEXITY_POINTS
    )
    override fun check(ktFiles: List<KtFile>) {
        val visitor = CyclomaticComplexityVisitor(maxComplexityAllowed)
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
