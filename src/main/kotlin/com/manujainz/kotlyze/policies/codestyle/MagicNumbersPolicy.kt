package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.MagicNumbersVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces the use of constants instead of magic numbers.
 *
 * Example:
 *  val retryCount = 3 // Use named constants for magic numbers.
 */
class MagicNumbersPolicy(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "Avoid magic numbers for better code readability and maintainability"

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = MagicNumbersVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
