package com.manujainz.kotlyze.policies.base.factory

import com.manujainz.kotlyze.policies.base.Policy
import com.manujainz.kotlyze.reporting.model.IssueType

interface PolicyFactoryInterface {

    fun getAllPolicies(): List<Policy>

    fun getPolicies(type: IssueType): List<Policy>
}