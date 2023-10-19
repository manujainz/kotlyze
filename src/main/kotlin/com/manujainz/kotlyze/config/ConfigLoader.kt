package com.manujainz.kotlyze.config

import com.manujainz.kotlyze.config.model.PolicyConfig

interface ConfigLoader {

    fun getConfigForPolicy(policy: String): PolicyConfig?

    fun isPolicyEnabled(policy: String): Boolean

}