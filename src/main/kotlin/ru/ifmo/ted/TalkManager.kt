package ru.ifmo.ted

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.model.Request
import ru.ifmo.ted.model.Talk
import ru.ifmo.ted.service.PersonService
import ru.ifmo.ted.service.RequestService
import ru.ifmo.ted.service.TalkService
import java.security.Principal


@Service
class TalkManager(
    val talkService: TalkService,
    val personService: PersonService,
    val requestService: RequestService,
    val applicationEventPublisher: ApplicationEventPublisher,
    val jmsTemplate: JmsTemplate
) {
    class RequestStateChangedEvent(source: Any, val person: Person, val title: String, val state: String) :
        ApplicationEvent(source)

    @Transactional
    fun findRequestByIdAndUpdateWithState(talkId: Long, requestId: Long, state: String) {
        val talk = talkService.findById(talkId)
        val request = talk.getRequestWithId(requestId)
        val person = personService.findByRequestId(requestId)
        changeRequestState(person, talk, request, state)
    }

    fun changeRequestState(person: Person, talk: Talk, request: Request, state: String) {
        val oldState = request.state
        val newState = requestService.changeRequestState(request, state)
        talkService.changeSpeakerCounterValue(talk, oldState, newState)
        talkService.save(talk)

        val event = RequestStateChangedEvent(this, person, talk.title, state)
        applicationEventPublisher.publishEvent(event)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: RequestStateChangedEvent) {
        val message = "Your request to attend ${event.title} has been ${event.state.toLowerCase()}."
        jmsTemplate.convertAndSend(
            "q_request_notifications",
            mapOf(
                "username" to event.person.username,
                "message" to message
            )
        )
    }

    fun postRequestForTalkWithId(value: Long, principal: Principal) {
        val person = personService.findBy(principal)
        val talk = talkService.findById(value)

        if (talk.hasRequestFor(person)) throw IllegalStateException("This person is already registered")

        talk.addRequestFor(person)
        talkService.save(talk)
    }

    fun getRequestsForTalkWithId(value: Long): Set<Request> {
        val talk = talkService.findById(value)
        return talk.requests
    }

    fun getAllTalks(): MutableIterable<Talk> {
        return talkService.getAllTalks()
    }

}
