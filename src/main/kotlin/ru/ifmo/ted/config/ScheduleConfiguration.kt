package ru.ifmo.ted.config

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import ru.ifmo.ted.job.PendingRequestCheckJob

@Profile("service")
@Configuration
class ScheduleConfiguration {

    @Bean
    fun jobDetail(): JobDetail? {
        return JobBuilder.newJob().ofType(PendingRequestCheckJob::class.java)
            .storeDurably()
            .withIdentity("PendingRequestCheckJob")
            .withDescription("Check pending messages.")
            .build()
    }

    @Bean
    fun trigger(): Trigger {
        return TriggerBuilder.newTrigger().forJob(jobDetail())
            .withIdentity("PendingRequestCheckTrigger")
            .withDescription("Trigger to check them pedning requests")
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(1))
            .build()
    }

    @Bean
    @Throws(SchedulerException::class)
    fun scheduler(trigger: Trigger, job: JobDetail, factory: SchedulerFactoryBean): Scheduler {
        val scheduler = factory.scheduler
        if (scheduler.checkExists(job.key)) scheduler.deleteJob(job.key)
        scheduler.scheduleJob(job, trigger)
        scheduler.start()
        return scheduler
    }

}
