package ru.ifmo.ted.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.ted.model.Request

@Service
class RequestService {

    @Transactional(propagation = Propagation.REQUIRED)
    fun changeRequestState(request: Request, state: String): Request.State {
        request.state = Request.State.valueOf(state.toUpperCase().trim())
        return request.state
    }

}