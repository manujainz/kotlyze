package com.manujainz.kotlyze.config

import com.manujainz.kotlyze.policies.base.Policy
import kotlin.reflect.KProperty

class PolicyConfigDelegate<T>(
    private val policy: Policy,
    private val key: String,
    private val default: T
    ) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return policy.configLoader.getConfigForPolicy(policy.policyId)?.properties?.get(key) as? T ?: default
    }
}
