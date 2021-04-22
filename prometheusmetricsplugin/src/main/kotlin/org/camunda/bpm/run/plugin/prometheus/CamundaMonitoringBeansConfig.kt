package org.camunda.bpm.run.plugin.prometheus

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@ImportResource("\${camunda.bpm.metrics.monitoringBeansXml:/file:/camunda/configuration/camunda-monitoring-beans.xml}")
class CamundaMonitoringBeansConfig(){

}