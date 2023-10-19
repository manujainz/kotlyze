package com.manujainz.kotlyze.config.model

data class PolicyConfig(
    val isEnabled: Boolean,
    val properties: Map<String, Any>
)
