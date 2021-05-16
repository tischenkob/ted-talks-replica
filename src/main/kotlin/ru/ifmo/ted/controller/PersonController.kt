package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.repository.NotificationRepository
import ru.ifmo.ted.repository.PersonRepository
import ru.ifmo.ted.service.PersonService
import java.security.Principal


@RestController
@RequestMapping("/api/people")
class PersonController(val personService: PersonService) {

    @GetMapping("/notifications")
    fun getNotifications(principal: Principal): Set<Notification> {
        return personService.getNotifications(principal)
    }

}
