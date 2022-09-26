# Spring Kafka Avro DLT DeserializationInfinite Problem

Need Kafka and Avro Schema Registry.  Dockers used in testing:
* confluentinc/cp-zookeeper
* confluentinc/cp-kafka
* confluentinc/cp-schema-registry
```sh
cd docker
docker-compose up
```

## Build
./gradlew clean build

## Run
./gradlew bootRun

## Test
1. Run successful message.
```sh
curl --location --request POST 'http://localhost:9001/examples' \
--header 'Content-Type: application/json' \
--data-raw '{
    "content": "success",
    "favoriteNumber": 1
}'
```
2. Run message to simulate error to enter into retry and DLT.
```sh
curl --location --request POST 'http://localhost:9001/examples' \
--header 'Content-Type: application/json' \
--data-raw '{
    "content": "fail",
    "favoriteNumber": 999
}'
```
3. Send a malformed message to topic.
```sh
kafka-console-producer --broker-list localhost:9092 --topic request.example
testing
<Ctrl-C>
```