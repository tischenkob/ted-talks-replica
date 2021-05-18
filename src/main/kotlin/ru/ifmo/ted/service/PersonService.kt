package ru.ifmo.ted.service

import org.springframework.stereotype.Service
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.repository.PersonRepository
import java.security.Principal

@Service
class PersonService(val personRepository: PersonRepository) {
    fun findBy(principal: Principal): Person {
        val username = principal.name
        return personRepository.findByUsername(username)!!  // !! is safe since the person must be authenticated
    }

    fun findByRequestId(value: Long): Person {
        return personRepository.findByRequestId(value)
    }
}