package ru.ifmo.ted.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.model.Talk
import ru.ifmo.ted.repository.TalkRepository

@Service
class TalkService(val talkRepository: TalkRepository) {
    private val pending = Request.State.PENDING
    private val approved = Request.State.APPROVED
    private val denied = Request.State.DENIED

    val pendingAndDeniedTransitions: Map<Request.State, Long> = mapOf(
        pending to 0,
        approved to 1,
        denied to 0
    )
    val approvedTransitions: Map<Request.State, Long> = mapOf(
        pending to -1,
        approved to 0,
        denied to -1
    )

    fun getAllTalks(): MutableIterable<Talk> {
        return talkRepository.findAll()
    }

    fun findById(value: Long): Talk {
        return talkRepository.findByIdOrNull(value) ?: throw IllegalStateException("Talk does not exist")
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun changeSpeakerCounterValue(talk: Talk, oldState: Request.State, newState: Request.State) {
        var transitions: Map<Request.State, Long> = pendingAndDeniedTransitions
        if (oldState == approved) transitions = approvedTransitions
        talk.requestCounter += transitions[newState]!!
    }

    fun save(talk: Talk) {
        talkRepository.save(talk)
    }

}
