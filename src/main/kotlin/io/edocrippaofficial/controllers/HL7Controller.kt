package io.edocrippaofficial.controllers

import io.edocrippaofficial.services.HL7Translator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class TranslateFromRequest(val message: String?)

fun Application.hl7Controller(
    hl7Service: HL7Translator
) {
    routing {
        post("/translate/from") {
            val logger = call.application.environment.log
            val body = call.receive<TranslateFromRequest>()
            val hl7message = body.message

            if (hl7message.isNullOrEmpty()) {
                logger.error("Required field message is empty")
                call.response.status(HttpStatusCode.BadRequest)
                call.respond(mapOf("error" to "Required field message is empty"))
                return@post
            }

            logger.trace("Body: {}", body.message)

            try {
                val res = hl7Service.convertToJson(body.message)
                call.respond(res)
            } catch (e: Exception) {
                logger.error("Error during the translation", e)
                call.response.status(HttpStatusCode.InternalServerError)
                call.respond(mapOf("error" to e.message))
            }
        }
    }
}