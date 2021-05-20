package ru.ifmo.ted.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlUserDetailsStore {

    @XmlElement(name = "user")
    List<XmlUserDetails> users;

    UserDetails findBy(String username) {
        for (UserDetails user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }
}