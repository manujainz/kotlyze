package com.manujainz.kotlyze.reporting.model

data class PolicyViolation(
    val issue: Issue,
    val issueType: IssueType,
    val policyId: String
)
