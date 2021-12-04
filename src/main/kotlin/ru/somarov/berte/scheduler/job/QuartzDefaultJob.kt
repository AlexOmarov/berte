package ru.somarov.berte.scheduler.job

import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import ru.somarov.berte.service.StubService

class QuartzDefaultJob(val service: StubService): QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        service.executeBusinessLogic()
    }
}