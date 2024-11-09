package io.edocrippaofficial.plugins

import io.edocrippaofficial.controllers.*
import io.edocrippaofficial.services.HL7Service
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        gson()
    }

    healthController()

    val hl7Service = HL7Service(log)
    hl7Controller(hl7Service)
}
