import io.micrometer.core.instrument.LongTaskTimer
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.camunda.bpm.engine.ProcessEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled

import javax.annotation.PostConstruct
import java.util.concurrent.atomic.AtomicLong

class ProcessInstanceMetrics {

    @Autowired MeterRegistry registry
    @Autowired ProcessEngine processEngine
//    @Autowired RuntimeService runtimeService

    String namespace = "camunda"
    String activeInstancesMetricName = "${namespace}_active_process_instances"
    String totalInstancesMetricName = "${namespace}_process_instances_total"
    String completedInstancesMetricName = "${namespace}_completed_process_instances"


    String metricDurationSuffix = "metric_execution_duration"
    def commonTags

    AtomicLong activeInstances
    AtomicLong totalInstances
    AtomicLong completedInstances

    LongTaskTimer activeInstancesExecuteTimer

    @PostConstruct
    void setup(){
        commonTags = [Tag.of("engineName", processEngine.getName())]

        activeInstances = registry.gauge(activeInstancesMetricName, commonTags, new AtomicLong(0))
        totalInstances = registry.gauge(totalInstancesMetricName, commonTags, new AtomicLong(0))
        completedInstances = registry.gauge(completedInstancesMetricName, commonTags, new AtomicLong(0))

        activeInstancesExecuteTimer = LongTaskTimer.builder("${activeInstancesMetricName}_${metricDurationSuffix}")
                .description("How long it took to query the process engine for the ${activeInstancesMetricName} metric")
                .tags(commonTags).register(registry)
    }


//    @Scheduled(fixedRate = 5000L)
//    void getActiveInstances(){
//        activeInstancesExecuteTimer.record(() -> {
//            activeInstances.set(processEngine.getHistoryService().createHistoricProcessInstanceQuery().active().count())
//        })
//    }

    @Scheduled(fixedRate = 5000L)
    void getActiveInstances(){
        activeInstancesExecuteTimer.record(() -> {
            activeInstances.set(processEngine.getRuntimeService().createProcessInstanceQuery().active().count())
        })
    }

    @Scheduled(fixedRate = 5000L)
    void getTotalInstances(){
        totalInstances.set(processEngine.getHistoryService().createHistoricProcessInstanceQuery().count())
    }

    @Scheduled(fixedRate = 5000L)
    void getCompletedInstances(){
        completedInstances.set(processEngine.getHistoryService().createHistoricProcessInstanceQuery().completed().count())
    }

}