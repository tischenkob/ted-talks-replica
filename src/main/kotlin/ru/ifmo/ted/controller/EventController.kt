package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.*
import ru.ifmo.ted.model.Event
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.service.EventService
import java.security.Principal

@RestController
@RequestMapping("/api/events")
class EventController(
    val eventService: EventService
) {

    @GetMapping
    fun getAllEvents(): MutableIterable<Event> {
        return eventService.getAllEvents()
    }

    @GetMapping("{id}/requests")
    fun getRequestsForEventWith(@PathVariable id: Long): Set<Request> {
        return eventService.getRequestsForEventWith(id)
    }

    @PostMapping("{id}/requests/{rid}")
    fun updateRequestWithState(
        @PathVariable id: Long,
        @PathVariable("rid") requestId: Long,
        @RequestBody state: String
    ) {
        eventService.updateRequestWithState(id, requestId, state)
    }

    @GetMapping("{id}/attend")
    fun postRequestForEventWith(@PathVariable id: Long, principal: Principal) {
        eventService.postRequestForEventWith(id, principal)
    }

}
