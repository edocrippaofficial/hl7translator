package io.edocrippaofficial.controllers

import io.edocrippaofficial.plugins.*
import io.ktor.server.testing.*
import kotlinx.serialization.Serializable
import kotlin.test.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class HealthControllerTest {
    @Test
    fun testHealthzRoute() = testApplication {
        application {
            configureRouting()
        }

        @Serializable
        data class Health(val status: String)

        val response = client.get("/-/healthz")
        val result: Health = Json.decodeFromString(response.bodyAsText())

        assertEquals(Health("OK"), result)
    }

    @Test
    fun testReadyRoute() = testApplication {
        application {
            configureRouting()
        }

        @Serializable
        data class Health(val status: String)

        val response = client.get("/-/ready")
        val result: Health = Json.decodeFromString(response.bodyAsText())

        assertEquals(Health("OK"), result)
    }
}
