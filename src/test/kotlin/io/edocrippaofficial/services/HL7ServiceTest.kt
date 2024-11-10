package io.edocrippaofficial.services

import org.slf4j.LoggerFactory
import kotlin.test.*

class HL7ServiceTest {

    @Test
    fun `test HL7 to JSON conversion`() {
        val hl7Message = "MSH|^~\\&|YourEHR|YourHospital|^MyEHR|MyClinic|202411101202||ADT^A28|5347022|P|2.3\r" +
                "EVN|A28|202411101202\r" +
                "PID||4000|4000||HINTZ^LUCIE^^MD||19431022|M||2054-5|45621 BRENNON SKYWAY^SUITE 855^NEW ANNETTA^MA^52211|GL|289-589-6398|737-832-6130||S||PATID4000^1^M10|647-316-5006|25199759^MA\r" +
                "NK1|1|AUGUST^GRADY^C|WRD|||||202411101202\r" +
                "NK1|2|SHERIDAN^FADEL^K|FND\r" +
                "PV1|1|I|1000^2024^01||||16^SCHMIDT^LOYAL|3098^JOHNSTON^ICIE||SUR||-|||\r" +
                "AL1|1||^NUTS||PRODUCES HIVES~RASH\r" +
                "AL1|2||^WHEAT\r" +
                "DG1|001|I10|1550|MAL NEO LIVER, PRIMARY|19880501103005|20240116210148||\r" +
                "PR1|2234|M11|690^CODE495||20240814020893\r" +
                "ROL|6^RECORDER^ROLE^ROLE MASTER LIST|AD|CP|MAUDE^WISOKY^HEATHER|20240128100136\r" +
                "GT1|1|2531|ARACELY^MOSCISKI^D\r" +
                "IN1|1|965062|694752|BCBS||||55442|\r" +
                "IN2|ID7542493|SSN647-316-5006\r" +
                "ROL|34^RECORDER^ROLE^ROLE MASTER LIST|AD|CP|EVA^BRUEN^GEORGE|20240109110112\r"

        val mappings = mapOf(
            "sendingApplication" to "MSH-3",
            "receivingApplication" to "MSH-5-2",
            "patient" to mapOf(
                "firstName" to "PID-5-2",
                "lastName" to "PID-5-1"
            )
        )

        val testLogger = LoggerFactory.getLogger("testLogger")
        val converter = HL7Service(testLogger, mappings)
        val result = converter.convertToJson(hl7Message)

        val expectedJson = mapOf(
            "sendingApplication" to "YourEHR",
            "receivingApplication" to "MyEHR",
            "patient" to mapOf(
                "firstName" to "LUCIE",
                "lastName" to "HINTZ"
            )
        )

        assertEquals(expectedJson, result)
    }
}