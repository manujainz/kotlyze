package com.manujainz.kotlyze.policies.codestyle

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.PolicyConfigDelegate
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.policies.base.visitFilesAndReport
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.visitors.ClassMethodVisitor
import org.jetbrains.kotlin.psi.KtFile

private const val DEFAULT_MAX_METHOD_PER_CLASS = 10
private const val KEY_MAX_METHOD_PER_CLASS = "maxMethodsAllowedPerClass"

private const val DEFAULT_MAX_METHOD_PARAMS = 6
private const val KEY_MAX_METHOD_PARAMS = "maxMethodParamsAllowed"

class MethodExplosionInClass(
    configLoader: ConfigLoader,
    private val reportEngine: ReportEngine
): Policy(configLoader) {

    override val policyId: String = this::class.java.simpleName

    override val issueType = IssueType.CODE_STYLE

    override val description = "There should be a threshold on the number of methods and params in a class"

    override val config = configLoader.getConfigForPolicy(policyId) ?: getDefaultPolicyConfig()

    private val maxMethodsAllowedPerClass by PolicyConfigDelegate(
        this,
        KEY_MAX_METHOD_PER_CLASS,
        DEFAULT_MAX_METHOD_PER_CLASS
    )

    private val maxMethodParamsAllowed by PolicyConfigDelegate(
        this,
        KEY_MAX_METHOD_PARAMS,
        DEFAULT_MAX_METHOD_PARAMS
    )

    override fun check(ktFiles: List<KtFile>) {
        val visitor = ClassMethodVisitor(maxMethodsAllowedPerClass, maxMethodParamsAllowed)
        visitFilesAndReport(ktFiles, visitor, reportEngine)
    }

}