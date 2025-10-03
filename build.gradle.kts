plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"
description = "MongoEmptyMapTest"
// val mockitoAgent = configurations.create("mockitoAgent")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.testcontainers:testcontainers-bom:1.21.3"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:junit-jupiter")
    // testImplementation("org.mockito:mockito-core")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // mockitoAgent("org.mockito:mockito-core:5.14.0") { isTransitive = false }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
//tasks {
//    test {
//        jvmArgs.add("-javaagent:${mockitoAgent.asPath}")
//    }
//}
