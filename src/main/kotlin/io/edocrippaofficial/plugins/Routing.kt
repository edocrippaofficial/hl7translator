package io.edocrippaofficial.plugins

import io.edocrippaofficial.controllers.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        gson()
    }

    healthController()
    hl7Controller()
}
