package com.manujainz.kotlyze.reporting.core

import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolation
import com.manujainz.kotlyze.reporting.publishers.ReportPublisher
import java.util.*
import kotlin.collections.ArrayList

class ReportEngineImpl: ReportEngine {

    private val violationReports = EnumMap<IssueType, MutableList<PolicyViolation>>(IssueType::class.java)

    override fun report(violation: PolicyViolation) {
        violationReports.computeIfAbsent(violation.issueType) {_ -> ArrayList()}.add(violation)
    }

    override fun notifyReporters(reportPublishers: List<ReportPublisher>) {
        reportPublishers.forEach {
            it.publish(violationReports)
        }
    }

}