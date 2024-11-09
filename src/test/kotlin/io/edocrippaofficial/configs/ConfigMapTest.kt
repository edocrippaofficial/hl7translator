package io.edocrippaofficial.configs

import java.io.File
import kotlin.test.*

class ConfigMapTest {

    private lateinit var tempFile: File
    private lateinit var configMapPath: String

    @BeforeTest
    fun setup() {
        tempFile = File.createTempFile("testConfig", ".json")
        tempFile.writeText("""
            {
                "key1": "value1",
                "key2": {
                    "nestedKey": "nestedValue",
                    "nestedNumber": 123
                },
                "key3": true
            }
        """.trimIndent())

        configMapPath = tempFile.absolutePath
    }

    @Test
    fun `loadConfigMap should load JSON file into Map structure`() {
        val configMap = loadConfigMap(configMapPath)

        assertNotNull(configMap)
        assertEquals("value1", configMap["key1"])

        val nestedMap = configMap["key2"] as? Map<*, *>
        assertNotNull(nestedMap)
        assertEquals("nestedValue", nestedMap["nestedKey"])
        assertEquals(123.0, nestedMap["nestedNumber"])

        assertEquals(true, configMap["key3"])
    }
}