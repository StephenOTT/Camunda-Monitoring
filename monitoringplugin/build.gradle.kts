import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val kotlinVersion: String by project
val spekVersion: String by project
val camundaVersion: String by project

plugins {
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    compileOnly(platform("org.camunda.bpm:camunda-bom:$camundaVersion"))
    compileOnly("org.camunda.bpm:camunda-engine")


    implementation("org.codehaus.groovy:groovy-all:3.0.7")

    compileOnly("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter")
    compileOnly("org.springframework.boot:spring-boot-starter-web:2.6.6")

    implementation("org.codehaus.groovy:groovy-all:3.0.7")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.6")
    implementation("io.micrometer:micrometer-registry-prometheus:latest.release")

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<ShadowJar> {
    archiveFileName.set("Camunda-Monitoring.jar")
}