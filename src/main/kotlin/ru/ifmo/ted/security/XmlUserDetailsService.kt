package ru.ifmo.ted.security

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.io.FileReader

class XmlUserDetailsService() : UserDetailsService {
    lateinit var store: XmlUserDetailsStore

    override fun loadUserByUsername(username: String?): UserDetails? {
        return store.findBy(username)
    }

    constructor(filePath: String) : this() {
        val context = JAXBContext.newInstance(XmlUserDetailsStore::class.java)
        val unmarshaller = context.createUnmarshaller()
        store = unmarshaller.unmarshal(FileReader(filePath)) as XmlUserDetailsStore
    }
}