import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled

import javax.annotation.PostConstruct
import java.util.concurrent.atomic.AtomicLong

class IncidentMetrics {

    @Autowired MeterRegistry registry
    @Autowired ProcessEngine processEngine
    @Autowired RuntimeService runtimeService

    String namespace = "camunda"
    String activeIncidentsMetricName = "${namespace}_active_incidents"

    def commonTags

    AtomicLong activeIncidents

    @PostConstruct
    void setup(){
        commonTags = [Tag.of("engineName", processEngine.getName())]
        activeIncidents = registry.gauge(activeIncidentsMetricName, commonTags, new AtomicLong(0))
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveIncidents(){
        activeIncidents.set(runtimeService.createIncidentQuery().count())
    }
}