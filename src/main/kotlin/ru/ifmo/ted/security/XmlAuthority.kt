package ru.ifmo.ted.security

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import org.springframework.security.core.GrantedAuthority

@XmlRootElement(name = "authority")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlAuthority(private val authority: String) : GrantedAuthority {
    @XmlElement
    override fun getAuthority() = authority
}