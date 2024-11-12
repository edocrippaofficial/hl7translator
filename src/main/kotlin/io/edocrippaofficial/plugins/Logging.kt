package io.edocrippaofficial.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import org.slf4j.MDC
import java.util.UUID

val excludedPaths = listOf("/-/healthz", "/-/ready")

val Logging = createApplicationPlugin(name = "LoggingPlugin") {
    onCallReceive { call ->
        if (excludedPaths.any { call.request.uri.startsWith(it) }) return@onCallReceive

        val reqId = call.request.header("X-Request-Id") ?: call.request.header("Request-Id") ?: UUID.randomUUID().toString()
        MDC.put("reqId", reqId)

        val uri = call.request.uri
        val httpMethod = call.request.httpMethod.value
        call.application.environment.log.trace("Incoming request: $httpMethod $uri")
    }

    on(ResponseSent) { call ->
        if (excludedPaths.any { call.request.uri.startsWith(it) }) return@on

        val uri = call.request.uri
        val httpMethod = call.request.httpMethod.value
        val status = call.response.status()
        call.application.environment.log.info("Response for: $httpMethod $uri: $status")

        MDC.remove("reqId")
    }
}