package com.example.calinews.config

import com.supercaliman.network.NetworkConfiguration

class ConfigurationNetwork:NetworkConfiguration{
    override fun baseUrl(): String {
        return "https://newsapi.org/v2/";
    }
}