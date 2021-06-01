package ru.ifmo.ted.job

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.context.annotation.Profile
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import ru.ifmo.ted.config.SecurityConfiguration
import ru.ifmo.ted.repository.RequestRepository

@Service
@Profile("service")
class PendingRequestCheckJob(
    val requestRepository: RequestRepository,
    val securityConfiguration: SecurityConfiguration,
    val jmsTemplate: JmsTemplate
) : Job {

    override fun execute(context: JobExecutionContext?) {
        val setOfRequests = requestRepository.findAllByStateEquals("PENDING")
        if (setOfRequests.isEmpty()) return

        val message = "There are ${setOfRequests.size} pending requests! Check them!!!"

        val adminUsernames = securityConfiguration.adminUsernameSet
        for (adminUsername in adminUsernames) {
            val notificationMap = mapOf(
                "username" to adminUsername,
                "message" to message
            )
            jmsTemplate.convertAndSend("q_request_notifications", notificationMap)
        }
    }

}
