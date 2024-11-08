package io.edocrippaofficial.services

import ca.uhn.hl7v2.HL7Exception
import ca.uhn.hl7v2.parser.Parser
import ca.uhn.hl7v2.parser.PipeParser
import ca.uhn.hl7v2.util.Terser

class HL7Service {
    private val parser: Parser = PipeParser()

    fun convert(hl7Message: String): Map<String, Any?> {
        val mappings = mapOf(
            "sendingApplication" to "MSH-3",
            "receivingApplication" to "MSH-5-2",
            "patient" to mapOf(
                "firstName" to "PID-5-2",
                "lastName" to "PID-5-1"
            )
        )

        val message = parser.parse(hl7Message)
        val terser = Terser(message)

        return parseMappings(mappings, terser)
    }

    private fun parseMappings(mappingConfig: Map<String, Any>, terser: Terser): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()

        mappingConfig.forEach { (jsonField, mappingValue) ->
            when (mappingValue) {
                is String -> {
                    try {
                        result[jsonField] = terser.get(mappingValue)
                    } catch (e: HL7Exception) {
                        println("Errore nell'estrazione del campo $jsonField: ${e.message}")
                    }
                }
                is Map<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    result[jsonField] = parseMappings(mappingValue as Map<String, Any>, terser)
                }
                else -> println("Formato di mapping non riconosciuto per $jsonField")
            }
        }

        return result
    }
}