package io.edocrippaofficial.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.healthController() {
    routing {
        route("/-") {
            get("/ready") {
                call.respond(mapOf("status" to "OK"))
            }

            get("/healthz") {
                call.respond(mapOf("status" to "OK"))
            }
        }
    }
}
