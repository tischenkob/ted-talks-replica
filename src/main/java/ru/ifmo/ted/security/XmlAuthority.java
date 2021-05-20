package ru.ifmo.ted.security;


import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authority")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlAuthority implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String value) {
        this.authority = value;
    }
}