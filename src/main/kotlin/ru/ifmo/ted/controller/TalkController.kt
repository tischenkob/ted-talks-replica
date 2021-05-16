package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.*
import ru.ifmo.ted.model.Talk
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.service.TalkService
import java.security.Principal

@RestController
@RequestMapping("/api/events")
class TalkController(
    val talkService: TalkService
) {

    @GetMapping
    fun getAllEvents(): MutableIterable<Talk> {
        return talkService.getAllTalks()
    }

    @GetMapping("{id}/requests")
    fun getRequestsForEventWith(@PathVariable id: Long): Set<Request> {
        return talkService.getRequestsForEventWith(id)
    }

    @PostMapping("{id}/requests/{rid}")
    fun updateRequestWithState(
        @PathVariable id: Long,
        @PathVariable("rid") requestId: Long,
        @RequestBody state: String
    ) {
        talkService.updateRequestWithState(id, requestId, state)
    }

    @GetMapping("{id}/attend")
    fun postRequestForEventWith(@PathVariable id: Long, principal: Principal) {
        talkService.postRequestForTalkWith(id, principal)
    }

}
