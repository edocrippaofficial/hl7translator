package io.edocrippaofficial.plugins

import io.edocrippaofficial.controllers.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }

    healthController()
}
