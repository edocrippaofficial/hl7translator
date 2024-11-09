package io.edocrippaofficial

import io.edocrippaofficial.configs.loadConfigMap
import io.edocrippaofficial.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val configMapPath = environment.config.property("ktor.configmap.path").getString()
    val configMap = loadConfigMap(configMapPath)

    configureRouting(configMap)
}
