package com.manujainz.kotlyze.policies.base

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.config.model.PolicyConfig
import org.jetbrains.kotlin.psi.KtFile
import com.manujainz.kotlyze.reporting.model.IssueType

abstract class Policy constructor(val configLoader: ConfigLoader) {

    abstract val policyId: String

    abstract val issueType: IssueType

    abstract val description: String

    abstract val config: PolicyConfig

    abstract fun check(ktFiles: List<KtFile>)

    fun getDefaultPolicyConfig() = PolicyConfig(isEnabled = false, mapOf())

}