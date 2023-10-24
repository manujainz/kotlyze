package com.manujainz.kotlyze.policies.base.factory

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.reporting.core.ReportEngine
import com.manujainz.kotlyze.reporting.model.IssueType

class PolicyFactory(
    private val config: ConfigLoader,
    private val reportEngine: ReportEngine
    ): PolicyFactoryInterface {

    private val repository = PolicyRepository(config, reportEngine)


    override fun getAllPolicies(): List<Policy> {
        return repository.policies.filter {
            config.isPolicyEnabled(it.policyId)
        }
    }

    override fun getPolicies(type: IssueType): List<Policy> {
        return repository.policies.filter {
           config.isPolicyEnabled(it.policyId) && it.issueType == type
        }
    }

}