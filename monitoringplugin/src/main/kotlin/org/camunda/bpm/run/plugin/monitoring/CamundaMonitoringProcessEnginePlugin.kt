package org.camunda.bpm.run.plugin.monitoring

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
open class CamundaMonitoringProcessEnginePlugin: AbstractProcessEnginePlugin() {

    private val logger = LoggerFactory.getLogger(CamundaMonitoringProcessEnginePlugin::class.java)

    override fun preInit(processEngineConfiguration: ProcessEngineConfigurationImpl?) {
        logger.info("Starting Camunda Monitoring Process Engine Plugin")
    }

}