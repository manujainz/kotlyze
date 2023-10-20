package com.manujainz.kotlyze.policies.factory

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.ComplexConditionChain
import com.manujainz.kotlyze.policies.MaxCharacterPerLine
import com.manujainz.kotlyze.reporting.core.ReportEngine

class PolicyRepository(configLoader: ConfigLoader, reportEngine: ReportEngine) {

    val policies by lazy {
        listOf(
            MaxCharacterPerLine(configLoader, reportEngine),
            ComplexConditionChain(configLoader, reportEngine)
        )
    }
}