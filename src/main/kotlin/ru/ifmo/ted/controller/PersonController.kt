package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.service.PersonService


@RestController
@RequestMapping("/api/people")
class PersonController(val personService: PersonService) {

    @GetMapping
    fun getPeople(): Set<Person> = personService.personRepository.findAll()
}
