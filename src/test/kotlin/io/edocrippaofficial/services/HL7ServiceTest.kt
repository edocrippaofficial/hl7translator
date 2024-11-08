package io.edocrippaofficial.services

import kotlin.test.*

class HL7ServiceTest {

    @Test
    fun `test HL7 to JSON conversion`() {
        val hl7Message = "MSH|^~\\&|LHA^2.16.840.1.113883.2.9.3.12.4.5.2||^APC||202401231035||ADT^A28^ADT_A05|8684533|P|2.5|||||ITA|ASCII\r" +
                "EVN||20240123103453\r" +
                "PID|||50440099^^^LHA^PI~PRFRSL89P42A944R^^^^NNITA||PERFETTI^ROSSELLA^^^^^L||19890902|F|||RESIDENZA PIAZZA MAGGIORE, 55&RESIDENZA PIAZZA MAGGIORE&55^^BOLOGNA^BO^40100^100^L^^037006~DOMICILIO VIA VERSI 15&DOMICILIO VIA VERSI&15^^MODENA^MO^41100^100^H^^036023~^^BOLOGNA^BO^40100^100^N^^037006||0557733222^PRN^^^^^0557733221~-^ORN^^^^^-~-^VHN^^^^^-~-^MAIL^^^^^-|||||||||||||100||||N|||20240123103453|hsadmintest\r" +
                "PD1|||BOLOGNA^^^^^^ASLA^^^080105~BOLOGNA^^^^^^ASLR^^^080105\r" +
                "PV1||N||||||||||||||||E\r" +
                "DB1|1|PT\r"

        val mappings = mapOf(
            "sendingApplication" to "MSH-3",
            "receivingApplication" to "MSH-5-2",
            "patient" to mapOf(
                "firstName" to "PID-5-2",
                "lastName" to "PID-5-1"
            )
        )

        val converter = HL7Service()
        val result = converter.convertToJson(hl7Message, mappings)

        val expectedJson = mapOf(
            "sendingApplication" to "LHA",
            "receivingApplication" to "APC",
            "patient" to mapOf(
                "firstName" to "ROSSELLA",
                "lastName" to "PERFETTI"
            )
        )

        assertEquals(expectedJson, result)
    }
}