package ru.ifmo.ted.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDate

data class Person(
    @Id
    val id: Long?,
    val name: String,
    val username: String,
    val password: String,
    val notifications: MutableSet<Notification>,
) {

    fun add(notification: Notification) {
        notifications.add(notification)
    }

}

data class Event(
    @Id
    val id: Long?,
    val title: String,
    val planned: LocalDate?,
    val description: String,
    val requests: MutableSet<Request>,
) {
    fun add(request: Request) {
        requests.add(request)
    }

    fun getRequestWithId(value: Long): Request? {
        return  requests.find { it.id == value }
    }
}

data class Request(
    @Id
    val id: Long?,
    @Column("person")
    val personId: Long,
    var state: State = State.PENDING,
) {
    constructor(person: Person) : this(null, personId = person.id!!)

    enum class State {
        PENDING, APPROVED, DENIED
    }
}


data class Notification(
    val message: String,
)
