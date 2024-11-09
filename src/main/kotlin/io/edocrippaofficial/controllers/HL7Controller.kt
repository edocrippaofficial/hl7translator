package io.edocrippaofficial.controllers

import io.edocrippaofficial.services.HL7Translator
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class TranslateFromRequest(val message: String)

fun Application.hl7Controller(
    hl7Service: HL7Translator
) {
    routing {
        post("/translate/from") {
            val logger = call.application.environment.log
            val body = call.receive<TranslateFromRequest>()

            val mappings = mapOf(
                "sendingApplication" to "MSH-3",
                "receivingApplication" to "MSH-5-2",
                "patient" to mapOf(
                    "firstName" to "PID-5-2",
                    "lastName" to "PID-5-1"
                )
            )

            logger.trace("Body: {}", body.message)
            logger.trace("Mappings: {}", mappings)


            val res = hl7Service.convertToJson(body.message, mappings)
            call.respond(res)
        }
    }
}