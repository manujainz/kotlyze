package com.manujainz.kotlyze.visitors.base

import com.manujainz.kotlyze.reporting.model.Issue

interface PolicyViolationListener {

    fun onPolicyViolated(issue: Issue)
}