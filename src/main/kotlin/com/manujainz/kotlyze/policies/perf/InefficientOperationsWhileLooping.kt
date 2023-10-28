package com.manujainz.kotlyze.policies.perf

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.InefficientOperationsWhileLoopingVisitor
import org.jetbrains.kotlin.psi.KtFile

/**
 * Policy to detect inefficient operations performed inside loops.
 * This includes string concatenations and object instantiations.
 *
 * Examples:
 * for(i in 1..10) {
 *     var str = "Hello" + "World"  // Inefficient string concatenation
 *     var obj = MyClass()          // Object instantiation
 * }
 */
class InefficientOperationsWhileLooping(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
) : Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.PERFORMANCE

    override val description = "Detects inefficient operations performed inside loops, " +
            "such as string concatenations and object instantiations."

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    override fun check(ktFiles: List<KtFile>) {
        val visitor = InefficientOperationsWhileLoopingVisitor()
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }
}

