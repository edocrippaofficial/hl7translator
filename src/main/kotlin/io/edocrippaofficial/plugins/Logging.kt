package io.edocrippaofficial.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import org.slf4j.MDC
import java.util.UUID

val Logging = createApplicationPlugin(name = "LoggingPlugin") {
    onCallReceive { call ->
        val reqId = call.request.header("X-Request-Id") ?: call.request.header("Request-Id") ?: UUID.randomUUID().toString()
        MDC.put("reqId", reqId)

        val uri = call.request.uri
        val httpMethod = call.request.httpMethod.value
        call.application.environment.log.trace("Incoming request: $httpMethod $uri")
    }

    on(ResponseSent) { call ->
        MDC.remove("reqId")

        val uri = call.request.uri
        val httpMethod = call.request.httpMethod.value
        val status = call.response.status()
        call.application.environment.log.info("Response for: $httpMethod $uri: $status")
    }
}