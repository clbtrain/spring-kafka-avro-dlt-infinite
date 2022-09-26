plugins {
    java
    id("org.springframework.boot") version ("2.6.3")
    id("io.spring.dependency-management") version ("1.0.13.RELEASE")
    id("com.github.davidmc24.gradle.plugin.avro") version ("1.3.0")
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

avro {
    fieldVisibility.set("PRIVATE")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

ext["log4j2.version"] = "2.17.1"

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    implementation("org.springframework.kafka:spring-kafka:2.8.8")
    implementation("org.apache.avro:avro:1.11.0")
    implementation("io.confluent:kafka-avro-serializer:7.2.0")
}
