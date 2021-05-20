package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.*
import ru.ifmo.ted.TalkManager
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.model.Talk
import java.security.Principal

@RestController
@RequestMapping("/api/talks")
class TalkController(val talkManager: TalkManager) {

    @GetMapping
    fun getAllEvents(): MutableIterable<Talk> {
        return talkManager.getAllTalks()
    }

    @GetMapping("{id}/requests")
    fun getRequestsForEventWith(@PathVariable("id") value: Long): Set<Request> {
        return talkManager.getRequestsForTalkWithId(value)
    }

    @PostMapping("{id}/requests/{rid}")
    fun updateRequestWithState(
        @PathVariable id: Long,
        @PathVariable("rid") requestId: Long,
        @RequestBody state: String
    ) {
        talkManager.findRequestByIdAndUpdateWithState(id, requestId, state)
    }

    @GetMapping("{id}/attend")
    fun postRequestForEventWith(@PathVariable("id") value: Long, principal: Principal) {
        talkManager.postRequestForTalkWithId(value, principal)
    }

}
