import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.impl.event.EventType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled

import javax.annotation.PostConstruct
import java.util.concurrent.atomic.AtomicLong

/**
 * The generic Camunda Monitoring metrics groovy file.
 *
 * This can be used as a quick start catch all file to add any metrics you want to monitor.
 *
 * Advanced use cases may wish to separate metrics into multiple groovy files.
 */
class CamundaMonitoringMetrics {

    @Autowired MeterRegistry registry
    @Autowired ProcessEngine processEngine

    String namespace = "camunda"

    List<Tag> commonTags

    String activeIncidentsMetricName = "${namespace}_active_incidents"
    AtomicLong activeIncidents

    String activeUserTasksMetricName = "${namespace}_active_user_tasks"
    AtomicLong activeUserTasks

    String activeMessageEventSubscriptionsMetricName = "${namespace}_active_message_event_subscriptions"
    AtomicLong activeMessageEventSubscriptions

    String activeSignalEventSubscriptionsMetricName = "${namespace}_active_signal_event_subscriptions"
    AtomicLong activeSignalEventSubscriptions

    String activeCompensateEventSubscriptionsMetricName = "${namespace}_active_compensate_event_subscriptions"
    AtomicLong activeCompensateEventSubscriptions

    String activeConditionalEventSubscriptionsMetricName = "${namespace}_active_conditional_event_subscriptions"
    AtomicLong activeConditionalEventSubscriptions

    String executableJobsMetricName = "${namespace}_executable_jobs"
    AtomicLong executableJobs

    String executableTimerJobsMetricName = "${namespace}_executable_timer_jobs"
    AtomicLong executableTimerJobs

    String timerJobsMetricName = "${namespace}_timer_jobs"
    AtomicLong timerJobs

    String messageJobsMetricName = "${namespace}_message_jobs"
    AtomicLong messageJobs

    String userCountMetricName = "${namespace}_user_count"
    AtomicLong userCount

    String tenantCountMetricName = "${namespace}_tenant_count"
    AtomicLong tenantCount

    String activeProcessInstancesMetricName = "${namespace}_active_process_instances"
    AtomicLong activeProcessInstances

    String completedProcessInstancesMetricName = "${namespace}_completed_process_instances"
    AtomicLong completedProcessInstances

    String activeProcessDefinitionsMetricName = "${namespace}_active_process_definitions"
    AtomicLong activeProcessDefinitions

    String deploymentsMetricName = "${namespace}_deployments"
    AtomicLong deployments

    String activeExternalTasksMetricName = "${namespace}_active_external_tasks"
    AtomicLong activeExternalTasks

    String activeLockedExternalTasksMetricName = "${namespace}_active_locked_external_tasks"
    AtomicLong activeLockedExternalTasks

    String activeNotLockedExternalTasksMetricName = "${namespace}_active_not_locked_external_tasks"
    AtomicLong activeNotLockedExternalTasks


    @PostConstruct
    void setup(){
        commonTags = [Tag.of("engineName", processEngine.getName())]
        activeIncidents = registry.gauge(activeIncidentsMetricName, commonTags, new AtomicLong(0))

        activeUserTasks = registry.gauge(activeUserTasksMetricName, commonTags, new AtomicLong(0))

        activeMessageEventSubscriptions = registry.gauge(activeMessageEventSubscriptionsMetricName, commonTags, new AtomicLong(0))

        activeSignalEventSubscriptions = registry.gauge(activeSignalEventSubscriptionsMetricName, commonTags, new AtomicLong(0))

        activeCompensateEventSubscriptions = registry.gauge(activeCompensateEventSubscriptionsMetricName, commonTags, new AtomicLong(0))

        activeConditionalEventSubscriptions = registry.gauge(activeConditionalEventSubscriptionsMetricName, commonTags, new AtomicLong(0))

        executableJobs = registry.gauge(executableJobsMetricName, commonTags, new AtomicLong(0))

        executableTimerJobs = registry.gauge(executableTimerJobsMetricName, commonTags, new AtomicLong(0))

        timerJobs = registry.gauge(timerJobsMetricName, commonTags, new AtomicLong(0))

        messageJobs = registry.gauge(messageJobsMetricName, commonTags, new AtomicLong(0))

        userCount = registry.gauge(userCountMetricName, commonTags, new AtomicLong(0))

        tenantCount = registry.gauge(tenantCountMetricName, commonTags, new AtomicLong(0))

        activeProcessInstances = registry.gauge(activeProcessInstancesMetricName, commonTags, new AtomicLong(0))

        completedProcessInstances = registry.gauge(completedProcessInstancesMetricName, commonTags, new AtomicLong(0))

        activeProcessDefinitions = registry.gauge(activeProcessDefinitionsMetricName, commonTags, new AtomicLong(0))

        deployments = registry.gauge(deploymentsMetricName, commonTags, new AtomicLong(0))

        activeExternalTasks = registry.gauge(activeExternalTasksMetricName, commonTags, new AtomicLong(0))

        activeLockedExternalTasks = registry.gauge(activeLockedExternalTasksMetricName, commonTags, new AtomicLong(0))

        activeNotLockedExternalTasks = registry.gauge(activeNotLockedExternalTasksMetricName, commonTags, new AtomicLong(0))
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveIncidents(){
        activeIncidents.set(processEngine.getRuntimeService().createIncidentQuery().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveUserTasks(){
        activeUserTasks.set(processEngine.getTaskService().createTaskQuery().active().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveMessageEventSubscriptions(){
        activeMessageEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.MESSAGE.name()).count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveSignalEventSubscriptions(){
        activeSignalEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.SIGNAL.name()).count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveCompensateEventSubscriptions(){
        activeCompensateEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.COMPENSATE.name()).count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveConditionalEventSubscriptions(){
        activeConditionalEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.CONDITONAL.name()).count())
    }

    @Scheduled(fixedRate = 60000L)
    void getExecutableJobs(){
        executableJobs.set(processEngine.getManagementService().createJobQuery().executable().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getExecutableTimerJobs(){
        executableTimerJobs.set(processEngine.getManagementService().createJobQuery().executable().timers().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getTimerJobs(){
        timerJobs.set(processEngine.getManagementService().createJobQuery().timers().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getMessageJobs(){
        messageJobs.set(processEngine.getManagementService().createJobQuery().messages().count())
    }

    @Scheduled(fixedRate = 36000000L) // Once an hour
    void getUserCount(){
        userCount.set(processEngine.getIdentityService().createUserQuery().count())
    }

    @Scheduled(fixedRate = 36000000L) // Once an hour
    void getTenantCount(){
        tenantCount.set(processEngine.getIdentityService().createTenantQuery().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveProcessInstances(){
        activeProcessInstances.set(processEngine.getRuntimeService().createProcessInstanceQuery().active().count())
    }

    @Scheduled(fixedRate = 36000000L) // Once an hour
    void getCompletedProcessInstances(){
        completedProcessInstances.set(processEngine.getHistoryService().createHistoricProcessInstanceQuery().completed().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveProcessDefinitions(){
        activeProcessDefinitions.set(processEngine.getRepositoryService().createProcessDefinitionQuery().active().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getDeployments(){
        deployments.set(processEngine.getRepositoryService().createDeploymentQuery().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveExternalTasks(){
        activeExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveLockedExternalTasks(){
        activeLockedExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().locked().count())
    }

    @Scheduled(fixedRate = 60000L)
    void getActiveNotLockedExternalTasks(){
        activeNotLockedExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().notLocked().count())
    }
}