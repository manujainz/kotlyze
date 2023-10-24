package com.manujainz.kotlyze.reporting.core

import com.manujainz.kotlyze.reporting.model.PolicyViolation
import com.manujainz.kotlyze.reporting.publishers.ReportPublisher

interface ReportEngine {

    fun report(violation: PolicyViolation)

    fun notifyReporters(reportPublishers: List<ReportPublisher>)

}