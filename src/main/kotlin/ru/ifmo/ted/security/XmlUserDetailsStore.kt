package ru.ifmo.ted.security

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlUserDetailsStore(@XmlElement val users: MutableCollection<XmlUserDetails>) {
    fun findBy(username: String?): UserDetails? {
        return users.find { it.username == username }
    }
}