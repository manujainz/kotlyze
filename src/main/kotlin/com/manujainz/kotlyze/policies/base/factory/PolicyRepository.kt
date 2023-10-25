package com.manujainz.kotlyze.policies.base.factory

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.complexity.ComplexConditionChain
import com.manujainz.kotlyze.policies.codestyle.MaxCharacterPerLine
import com.manujainz.kotlyze.policies.codestyle.MethodExplosionInClass
import com.manujainz.kotlyze.policies.perf.CoroutineGlobalScopeUsage
import com.manujainz.kotlyze.reporting.core.ReportEngine

class PolicyRepository(configLoader: ConfigLoader, reportEngine: ReportEngine) {

    val policies by lazy {
        listOf(
            MaxCharacterPerLine(configLoader, reportEngine),
            ComplexConditionChain(configLoader, reportEngine),
            CoroutineGlobalScopeUsage(configLoader, reportEngine),
            MethodExplosionInClass(configLoader, reportEngine)
        )
    }
}