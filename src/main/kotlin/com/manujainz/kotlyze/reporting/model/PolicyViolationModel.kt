package com.manujainz.kotlyze.reporting.model

data class PolicyViolationModel(
    val issue: Issue,
    val issueType: IssueType,
    val policyId: String
)
