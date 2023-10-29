package com.manujainz.kotlyze.policies.base.factory

import com.manujainz.kotlyze.config.ConfigLoader
import com.manujainz.kotlyze.policies.codestyle.*
import com.manujainz.kotlyze.policies.complexity.*
import com.manujainz.kotlyze.policies.perf.*
import com.manujainz.kotlyze.reporting.core.ReportEngine

class PolicyRepository(configLoader: ConfigLoader, reportEngine: ReportEngine) {

    val policies by lazy {
        setOf(
            MagicNumbersPolicy(configLoader, reportEngine),
            MaxCharacterPerLine(configLoader, reportEngine),
            MethodExplosionInClass(configLoader, reportEngine),
            MultipleClassesPerFile(configLoader, reportEngine),
            TrailingWhitespace(configLoader, reportEngine),
            UnusedImportsPolicy(configLoader, reportEngine),
            ComplexConditionChain(configLoader, reportEngine),
            ExcessiveReturnStatements(configLoader, reportEngine),
            HighCyclomaticComplexity(configLoader, reportEngine),
            NestedDepth(configLoader, reportEngine),
            RecursiveFunctionUsage(configLoader, reportEngine),
            BlockingCallInCoroutine(configLoader, reportEngine),
            CoroutineGlobalScopeUsage(configLoader, reportEngine),
            InefficientCollectionOperation(configLoader, reportEngine),
            InefficientOperationsWhileLooping(configLoader, reportEngine),
            LargeBitmapLoading(configLoader, reportEngine),
        )
    }
}