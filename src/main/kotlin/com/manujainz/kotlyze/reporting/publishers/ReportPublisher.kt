package com.manujainz.kotlyze.reporting.publishers

import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import java.util.EnumMap

interface ReportPublisher {

    fun publish(violations: EnumMap<IssueType, MutableList<PolicyViolation>>)
}