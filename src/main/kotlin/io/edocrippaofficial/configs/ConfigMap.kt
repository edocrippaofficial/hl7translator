package io.edocrippaofficial.configs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException

fun loadConfigMap(path: String): Map<String, Any> {

    val configFile = File(path)
    if (!configFile.exists()) {
        throw FileNotFoundException("No configuration file found at: $path")
    }

    val type = object : TypeToken<Map<String, Any>>() {}.type

    return configFile.reader().use { reader ->
        Gson().fromJson(reader, type)
    }
}