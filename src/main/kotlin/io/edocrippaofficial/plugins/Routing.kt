package io.edocrippaofficial.plugins

import io.edocrippaofficial.configs.loadConfigMap
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

    val configMapPath = environment.config.property("ktor.configmap.path").getString()
    val configMap = loadConfigMap(configMapPath)

    val mappings = mapOf(
        "sendingApplication" to "MSH-3",
        "receivingApplication" to "MSH-5-2",
        "patient" to mapOf(
            "firstName" to "PID-5-2",
            "lastName" to "PID-5-1"
        )
    )

    val hl7Service = HL7Service(log, configMap)
    hl7Controller(hl7Service)
}
