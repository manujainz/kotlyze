package com.manujainz.kotlyze.policies.perf

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.BitmapVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Enforces best practices for loading Bitmaps to avoid memory issues.
 *
 * Checks for direct uses of BitmapFactory without resizing or limiting options.
 */
class LargeBitmapLoading(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.PERFORMANCE

    override val description = "Prevent loading of large bitmaps which can cause memory issues."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = BitmapVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}
