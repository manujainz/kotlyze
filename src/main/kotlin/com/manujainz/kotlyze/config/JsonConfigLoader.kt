package com.manujainz.kotlyze.config

import com.manujainz.kotlyze.config.model.PolicyConfig
import org.json.JSONObject

private const val KEY_ENABLED = "enabled"
private const val KEY_POLICIES = "policies"

class JsonConfigLoader(jsonConfig: String): ConfigLoader {

    private var configs: Map<String, PolicyConfig>

    init {
        configs = parse(jsonConfig)
    }

    fun printConfig() {
        configs.forEach {
            println(it.key + "=" + it.value)
        }
    }

    private fun parse(jsonConfig: String): Map<String, PolicyConfig> {
        try {
            val rootObject = JSONObject(jsonConfig)
            val policiesObject = rootObject.getJSONObject(KEY_POLICIES)

            return policiesObject.keys().asSequence().associateWith {
                val policyObject = policiesObject.getJSONObject(it)
                PolicyConfig(
                    policyObject.getBoolean(KEY_ENABLED),
                    policyObject.keySet()
                        .filter { key -> key != KEY_ENABLED }
                        .associateWith { key -> policyObject.get(key) }
                )
            }
        } catch (e: Exception) {
            println("Exception when parsing json config")
            e.printStackTrace()
            return emptyMap()
        }
    }


    override fun getConfigForPolicy(policy: String) = configs[policy]

    override fun isPolicyEnabled(policy: String) = configs[policy]?.isEnabled ?: false
}