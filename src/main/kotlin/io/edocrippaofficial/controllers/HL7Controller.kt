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

            logger.trace("Body: {}", body.message)

            val res = hl7Service.convertToJson(body.message)
            call.respond(res)
        }
    }
}