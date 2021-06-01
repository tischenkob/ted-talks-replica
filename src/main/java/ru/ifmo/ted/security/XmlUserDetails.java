package ru.ifmo.ted.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlUserDetails implements UserDetails {

    @Getter
    @XmlAttribute(name = "username")
    private String username = "empty";

    @Getter
    @XmlAttribute(name = "password")
    private String password = "none";

    @XmlElement(name = "authority")
    private List<String> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String authority: authorities) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User " + username + ':' + password + ' ' + "[" + authorities + ']';
    }
}
