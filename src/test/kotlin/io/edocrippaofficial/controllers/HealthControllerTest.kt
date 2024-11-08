package io.edocrippaofficial.controllers

import com.google.gson.Gson
import io.edocrippaofficial.plugins.*
import io.ktor.server.testing.*
import kotlinx.serialization.Serializable
import kotlin.test.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class HealthControllerTest {
    @Test
    fun `test Healthz Route`() = testApplication {
        application {
            configureRouting()
        }

        @Serializable
        data class Health(val status: String)

        val response = client.get("/-/healthz")
        val result = Gson().fromJson(response.bodyAsText(), Health::class.java)

        assertEquals(Health("OK"), result)
    }

    @Test
    fun `test Ready Route`() = testApplication {
        application {
            configureRouting()
        }

        @Serializable
        data class Health(val status: String)

        val response = client.get("/-/ready")
        val result = Gson().fromJson(response.bodyAsText(), Health::class.java)

        assertEquals(Health("OK"), result)
    }
}
