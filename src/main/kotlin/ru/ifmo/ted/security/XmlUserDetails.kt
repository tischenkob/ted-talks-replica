package ru.ifmo.ted.security

import jakarta.xml.bind.annotation.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlUserDetails(
    @XmlAttribute
    private val username: String,
    @XmlAttribute
    private val password: String,
    @XmlElement
    private val authorities: MutableCollection<XmlAuthority>
) : UserDetails {

    override fun getAuthorities() = authorities
    override fun getPassword() = password
    override fun getUsername() = username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}