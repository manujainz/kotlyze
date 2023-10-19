package com.manujainz.kotlyze.config

import com.manujainz.kotlyze.config.model.PolicyConfig
import org.json.JSONObject

private const val KEY_ENABLED = "enabled"

class JsonConfigLoader(jsonConfig: String): ConfigLoader {

    private var configs: Map<String, PolicyConfig>

    init {
        configs = parse(jsonConfig)
    }

    private fun parse(jsonConfig: String): Map<String, PolicyConfig> {
        val jsonObject = JSONObject(jsonConfig)

        return jsonObject.keys().asSequence().associateWith {
            val policyObject = jsonObject.getJSONObject(it)
            PolicyConfig(
                policyObject.getBoolean(KEY_ENABLED),
                policyObject.keySet()
                    .filter { key -> key != KEY_ENABLED }
                    .associateWith { key -> policyObject.get(key) }
            )
        }
    }


    override fun getConfigForPolicy(policy: String) = configs[policy]

    override fun isPolicyEnabled(policy: String) = configs[policy]?.isEnabled ?: false
}