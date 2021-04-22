package com.github.stephenott.camunda.springboot.monitoring

import org.camunda.bpm.run.plugin.monitoring.CamundaMonitoringProcessEnginePlugin
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackageClasses = [CamundaMonitoringProcessEnginePlugin::class])
@EnableProcessApplication
class Application

fun main(args: Array<String>) = runApplication<Application>(*args).let { }
