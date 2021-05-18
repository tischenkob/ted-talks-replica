package ru.ifmo.ted.service

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

    fun sendNotification(person: Person, message: String) {
        val notification = Notification(message)
        person.add(notification)
        personRepository.save(person)
    }
}