package ru.ifmo.ted.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.ifmo.ted.model.Event
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.repository.RequestRepository
import ru.ifmo.ted.repository.EventRepository
import ru.ifmo.ted.repository.NotificationRepository
import ru.ifmo.ted.repository.PersonRepository
import java.lang.IllegalStateException
import java.security.Principal

@RestController
@RequestMapping("/api/events")
class EventController(
    val eventRepository: EventRepository,
    val requestRepository: RequestRepository,
    val personRepository: PersonRepository,
    val notificationRepository: NotificationRepository
) {

    @GetMapping
    fun getAllEvents(): MutableIterable<Event> {
        return eventRepository.findAll()
    }

    @GetMapping("{id}/requests")
    fun getRequestsForEventWith(@PathVariable id: Long): Set<Request> {
        val event = eventRepository.findById(id).orElseThrow()
        return event.requests
    }

    @GetMapping("{id}/requests/{rid}")
    fun updateRequestWithState(
        @PathVariable id: Long,
        @PathVariable("rid") requestId: Long,
        state: String
    ) {
        val event = eventRepository.findById(id)

        if (event.isEmpty) throw IllegalStateException("Event does not exist")

        val request = event.get().getRequestWithId(requestId) ?: throw IllegalStateException("Request does not exist")

        request.state = Request.State.valueOf(state)
        eventRepository.save(event.get())

        val person = personRepository.findByRequestId(requestId)
        val notification =
            Notification("Your request to attend ${event.get().title} has been ${state.toLowerCase()}.")
        person.add(notification)
        personRepository.save(person)
    }

    @GetMapping("{id}/attend")
    fun postRequestForEventWith(@PathVariable id: Long, principal: Principal) {
        val username = principal.name
        val person = personRepository.findByUsername(username)!!
        val request = Request(person)

        val event = eventRepository.findById(id)
        if (event.isPresent) {
            event.get().add(request)
            eventRepository.save(event.get())
        } else throw IllegalStateException("There is no such event")

    }

}
