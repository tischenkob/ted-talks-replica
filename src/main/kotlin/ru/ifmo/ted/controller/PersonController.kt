package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.repository.NotificationRepository
import ru.ifmo.ted.repository.PersonRepository
import java.security.Principal


@RestController
@RequestMapping("/api/people")
class PersonController(val personRepository: PersonRepository) {

    @GetMapping("/notifications")
    fun getNotificationsForPersonWithId(principal: Principal): Set<Notification> {
        val username = principal.name
        val currentUser = personRepository.findByUsername(username)!!
        return currentUser.notifications
    }

}
