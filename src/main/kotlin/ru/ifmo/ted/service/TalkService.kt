package ru.ifmo.ted.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ifmo.ted.model.Talk
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.repository.TalkRepository
import ru.ifmo.ted.repository.PersonRepository
import java.security.Principal

@Service
class TalkService(
    val talkRepository: TalkRepository,
    val personRepository: PersonRepository,
) {

    fun getAllTalks(): MutableIterable<Talk> {
        return talkRepository.findAll()
    }

    fun getRequestsForEventWith(id: Long): Set<Request> {
        val talk = talkRepository.findByIdOrNull(id) ?: throw IllegalStateException("Talk does not exist")
        return talk.requests
    }

    fun updateRequestWithState(id: Long, requestId: Long, state: String) {
        val talk = talkRepository.findByIdOrNull(id) ?: throw IllegalStateException("Talk does not exist")
        val request = talk.getRequestWithId(requestId) ?: throw IllegalStateException("Request does not exist")
        request.state = Request.State.valueOf(state)
        talkRepository.save(talk)

        onRequestStateChanged(requestId, talk, state)
    }

    private fun onRequestStateChanged(requestId: Long, talk: Talk, state: String) {
        val person = personRepository.findByRequestId(requestId)
        val notification = Notification("Your request to attend ${talk.title} has been ${state.toLowerCase()}.")
        person.add(notification)
        personRepository.save(person)
    }

    fun postRequestForTalkWith(id: Long, principal: Principal) {
        val username = principal.name
        val person = personRepository.findByUsername(username)!! // !! is safe since the person must be authenticated
        val talk = talkRepository.findByIdOrNull(id) ?: throw IllegalStateException("There is no such talk")
        
        val request = Request(person)
        talk.add(request)
        talkRepository.save(talk)
    }


}