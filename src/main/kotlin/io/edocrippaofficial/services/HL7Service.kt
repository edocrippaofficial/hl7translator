package io.edocrippaofficial.services

import ca.uhn.hl7v2.HL7Exception
import ca.uhn.hl7v2.parser.Parser
import ca.uhn.hl7v2.parser.PipeParser
import ca.uhn.hl7v2.util.Terser

interface HL7Translator {
    fun convertToJson(hl7Message: String, mappings: Map<String, Any>): Map<String, Any?>
}

class HL7Service : HL7Translator {
    private val parser: Parser = PipeParser()

    override fun convertToJson(hl7Message: String, mappings: Map<String, Any>): Map<String, Any?> {
        val message = parser.parse(hl7Message)
        val terser = Terser(message)

        val result = buildHL7FromMap(mappings, terser)
        return result
    }

    private fun buildHL7FromMap(mappingConfig: Map<String, Any>, terser: Terser): Map<String, Any?> {
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
                    result[jsonField] = buildHL7FromMap(mappingValue as Map<String, Any>, terser)
                }
                else -> println("Formato di mapping non riconosciuto per $jsonField")
            }
        }

        return result
    }
}