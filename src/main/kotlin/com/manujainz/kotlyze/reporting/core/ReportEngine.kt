package com.manujainz.kotlyze.reporting.core

import com.manujainz.kotlyze.reporting.model.PolicyViolationModel
import com.manujainz.kotlyze.reporting.publishers.ReportPublisher

interface ReportEngine {

    fun report(violation: PolicyViolationModel)

    fun notifyReporters(reportPublishers: List<ReportPublisher>)

}