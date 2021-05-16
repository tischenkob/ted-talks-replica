package ru.ifmo.ted.service

import org.springframework.stereotype.Service
import ru.ifmo.ted.model.Event
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.repository.EventRepository
import ru.ifmo.ted.repository.PersonRepository
import java.security.Principal

@Service
class EventService(
    val eventRepository: EventRepository,
    val personRepository: PersonRepository,
) {

    fun getAllEvents(): MutableIterable<Event> {
        return eventRepository.findAll()
    }

    fun getRequestsForEventWith(id: Long): Set<Request> {
        val event = eventRepository.findById(id).orElseThrow()
        return event.requests
    }

    fun updateRequestWithState(id: Long, requestId: Long, state: String) {
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

    fun postRequestForEventWith(id: Long, principal: Principal) {
        val username = principal.name
        val person = personRepository.findByUsername(username)!!
        val request = Request(person)
        val event = eventRepository.findById(id)

        if (event.isPresent) {
            event.get().add(request)
            eventRepository.save(event.get())
        } else throw java.lang.IllegalStateException("There is no such event")

    }


}