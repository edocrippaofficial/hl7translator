package io.edocrippaofficial

import io.edocrippaofficial.configs.loadConfigMap
import io.edocrippaofficial.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val configMapPath = environment.config.property("ktor.configmap.path").getString()
    val configMap = loadConfigMap(configMapPath)

    install(CallLogging) {
        filter { call ->
            !call.request.path().startsWith("/-/")
        }
        format { call ->
            val uri = call.request.uri
            val httpMethod = call.request.httpMethod.value
            val status = call.response.status()
            "Response for: $httpMethod $uri: $status"
        }
    }

    configureRouting(configMap)

    log.info("Router configured")
}
