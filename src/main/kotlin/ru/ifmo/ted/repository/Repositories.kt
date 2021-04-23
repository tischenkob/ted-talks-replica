package ru.ifmo.ted.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.model.Person
import ru.ifmo.ted.model.Event
import ru.ifmo.ted.model.Request

interface PersonRepository : CrudRepository<Person, Long> {
    @Query("SELECT p.* FROM ted.person p WHERE username = :uname")
    fun findByUsername(@Param("uname") value: String): Person?

    @Query("SELECT * FROM ted.person")
    override fun findAll(): Set<Person>

    @Query("SELECT p.* FROM ted.request r JOIN ted.person p ON r.person = p.id WHERE r.id = :value")
    fun findByRequestId(@Param("value") value: Long): Person
}

interface EventRepository : CrudRepository<Event, Long>

interface RequestRepository : CrudRepository<Request, Long> {
    @Query("SELECT * FROM ted.request WHERE event = :event_id")
    fun findByAllEventId(@Param("event_id") value: Long): Set<Request>

}

interface NotificationRepository : CrudRepository<Notification, Long> {
    @Query("SELECT * FROM ted.person p WHERE id = :id")
    fun findAllByPersonId(@Param("id") value: Long): Set<Notification>
}
