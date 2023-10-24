package com.manujainz.kotlyze.reporting.publishers

import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import java.util.*

class SystemLogReportPublisher: ReportPublisher {

    override fun publish(violations: EnumMap<IssueType, MutableList<PolicyViolation>>) {
        violations.forEach {
            println("IssueType: ${it.key}")
            it.value.forEach { violations ->
                println(violations)
            }
        }
    }
}