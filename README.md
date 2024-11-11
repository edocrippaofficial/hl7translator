# HL7 Translator

A simple HTTP server to translate HL7 messages into custom 

## Docker Image

There is a Docker image available on [Docker Hub](https://hub.docker.com/r/edocrippaofficial/hl7translator)

To run it locally just run the following command:

```bash
docker run -it \
-p 3000:3000  \
-v ./translationMap.json:/app/config/translationMap.json \
-e HTTP_PORT=3000 \
-e CONFIGMAP_PATH=/app/config/translationMap.json \
-e LOG_LEVEL=trace \
edocrippaofficial/hl7translator
```

## Build and Run

### Jar

To build and run the jar file, run the following command:

```bash
./gradlew clean build && \
export CONFIGMAP_PATH=./translationMap.json && \
export LOG_LEVEL=trace && \
java -jar build/libs/application.jar
```

### Docker

To build and run the Docker image, run the following command:

```bash
./gradlew clean build && \
docker build -t hl7translator . && \
docker run -it \
-p 3000:3000  \
-v ./translationMap.json:/app/config/translationMap.json \
-e HTTP_PORT=3000 \
-e CONFIGMAP_PATH=/app/config/translationMap.json \
-e LOG_LEVEL=trace \
hl7translator
```

## Example

With the configuration file `translationMap.json`, making the following cURL request:

```bash
curl --location 'localhost:3000/translate/from' \
--header 'Content-Type: application/json' \
--data '{
    "message": "MSH|^~\\&|YourEHR|YourHospital|^MyEHR|MyClinic|202411101202||ADT^A28|5347022|P|2.3\rEVN|A28|202411101202\rPID||4000|4000||HINTZ^LUCIE^^MD||19431022|M||2054-5|45621 BRENNON SKYWAY^SUITE 855^NEW ANNETTA^MA^52211|GL|289-589-6398|737-832-6130||S||PATID4000^1^M10|647-316-5006|25199759^MA\rNK1|1|AUGUST^GRADY^C|WRD|||||202411101202\rNK1|2|SHERIDAN^FADEL^K|FND\rPV1|1|I|1000^2024^01||||16^SCHMIDT^LOYAL|3098^JOHNSTON^ICIE||SUR||-|||\rAL1|1||^NUTS||PRODUCES HIVES~RASH\rAL1|2||^WHEAT\rDG1|001|I10|1550|MAL NEO LIVER, PRIMARY|19880501103005|20240116210148||\rPR1|2234|M11|690^CODE495||20240814020893\rROL|6^RECORDER^ROLE^ROLE MASTER LIST|AD|CP|MAUDE^WISOKY^HEATHER|20240128100136\rGT1|1|2531|ARACELY^MOSCISKI^D\rIN1|1|965062|694752|BCBS||||55442|\rIN2|ID7542493|SSN647-316-5006\rROL|34^RECORDER^ROLE^ROLE MASTER LIST|AD|CP|EVA^BRUEN^GEORGE|20240109110112"
}' 
```

will return the following response:

```json
{
    "sendingApplication": "YourEHR",
    "receivingApplication": "MyEHR",
    "patient": {
        "firstName": "HINTZ",
        "lastName": "LUCIE"
    }
}
```