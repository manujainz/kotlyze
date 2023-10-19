package com.manujainz.kotlyze.reporting.core

import com.manujainz.kotlyze.reporting.model.IssueType
import com.manujainz.kotlyze.reporting.model.PolicyViolationModel
import com.manujainz.kotlyze.reporting.publishers.ReportPublisher
import java.util.*
import kotlin.collections.ArrayList

class ReportEngineImpl: ReportEngine {

    private val violationReports = EnumMap<IssueType, MutableList<PolicyViolationModel>>(IssueType::class.java)

    override fun report(violation: PolicyViolationModel) {
        violationReports.computeIfAbsent(violation.issueType) {_ -> ArrayList()}.add(violation)
    }

    override fun notifyReporters(reportPublishers: List<ReportPublisher>) {
        reportPublishers.forEach {
            it.publish(violationReports)
        }
    }

}