package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.MultipleClassesPerFileVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces a single top-level class or object per file.
 *
 * Example:
 *  // File MultipleClasses.kt
 *  class FirstClass {}   // OK
 *  class SecondClass {}  // This shouldn't be in the same file.
 */
class MultipleClassesPerFile(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "Ensure only one top-level class or object is present per file."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = MultipleClassesPerFileVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
