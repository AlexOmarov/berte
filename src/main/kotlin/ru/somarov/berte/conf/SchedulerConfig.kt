package ru.somarov.berte.conf

import org.quartz.*
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean
import ru.somarov.berte.scheduler.job.QuartzDefaultJob
import java.time.Duration


@Configuration
class SchedulerConfig {

    private val logger = LoggerFactory.getLogger(SchedulerConfig::class.java)

    @Bean
    fun jobDetail(): JobDetail {
        return JobBuilder.newJob().ofType(QuartzDefaultJob::class.java)
            .storeDurably()
            .withIdentity("Qrtz_Job_Detail")
            .withDescription("Invoke QuartzDefaultJob service...")
            .build()
    }

    @Bean
    fun triggerFactory(job: JobDetail): SimpleTriggerFactoryBean {
        val trigger = SimpleTriggerFactoryBean()
        trigger.setJobDetail(job)
        trigger.setRepeatInterval(Duration.ofMinutes(1).toMillis())
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
        return trigger
    }

    @Bean
    @Throws(SchedulerException::class)
    fun scheduler(trigger: Trigger, job: JobDetail, factory: SchedulerFactoryBean): Scheduler {
        val scheduler: Scheduler = factory.scheduler
        if (scheduler.checkExists(job.key))  logger.info("Job ${job.key} already exists") else scheduler.scheduleJob(job, trigger)
        scheduler.start()
        return scheduler
    }

}