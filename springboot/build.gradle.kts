val kotlinVersion: String by project
val spekVersion: String by project
val camundaVersion: String by project

plugins {
    application
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.spring")
//    id("org.springframework.boot") version "2.4.5"
//    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}


dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.4.5"))
    implementation(platform("org.camunda.bpm:camunda-bom:$camundaVersion"))

    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("org.camunda.bpm:camunda-engine-plugin-spin")
    implementation("org.camunda.spin:camunda-spin-dataformat-json-jackson")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.3")

    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

    runtimeOnly("com.h2database:h2:1.4.200")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation(project(":monitoringplugin"))

}

application {
    // notice the Kt at the end!!
    mainClassName = "com.github.stephenott.camunda.springboot.monitoring.ApplicationKt"
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
