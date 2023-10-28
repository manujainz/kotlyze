package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.UnusedImportsVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces clean code by highlighting unused import statements.
 *
 * Example:
 *  import com.example.UnusedClass // This import is unused.
 */
class UnusedImportsPolicy(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "Remove unused import statements for cleaner code."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = UnusedImportsVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
