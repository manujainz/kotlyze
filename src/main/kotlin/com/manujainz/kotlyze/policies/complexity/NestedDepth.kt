package com.manujainz.kotlyze.policies.complexity

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.visitors.NestedDepthVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces a maximum nesting depth in the code to ensure readability and maintainability.
 *
 * Example:
 *  if (...) {
 *      if (...) {
 *          while (...) {
 *              for (...) {  // Exceeds maximum allowed nesting depth if threshold is 3
 *              }
 *          }
 *      }
 *  }
 */

private const val KEY_ALLOWED_MAX_NESTED_DEPTH = "maxAllowedNestedDepth"
private const val DEFAULT_ALLOWED_MAX_NESTED_DEPTH = 4

class NestedDepth(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.COMPLEXITY

    override val description = "Avoid excessive nesting to improve code readability"

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxNestingDepth by PolicyConfigDelegate(
        this,
        KEY_ALLOWED_MAX_NESTED_DEPTH,
        DEFAULT_ALLOWED_MAX_NESTED_DEPTH
    )

    override fun check(ktFiles: List<KtFile>) {
        val visitor = NestedDepthVisitor(maxNestingDepth)
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
