package ru.ifmo.ted.service

import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.repository.PersonRepository
import java.security.Principal

@Service
class NotificationService(val personRepository: PersonRepository) {

    fun getNotifications(principal: Principal): Set<Notification> {
        val username = principal.name
        val currentUser = personRepository.findByUsername(username)!!
        return currentUser.notifications
    }

    @Profile("service")
    @JmsListener(destination = "q_request_notifications")
    fun sendNotification(notificationInfo: Map<String, String>) {
        // it's okay to !! here since this method will be called only by the system and only after auth
        val person: Person = personRepository.findByUsername(notificationInfo["username"]!!)!!
        val message: String = notificationInfo["message"]!!
        val notification = Notification(message)
        person.add(notification)
        personRepository.save(person)
    }
}
