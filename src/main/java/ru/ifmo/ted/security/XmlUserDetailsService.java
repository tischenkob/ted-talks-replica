package ru.ifmo.ted.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class XmlUserDetailsService implements UserDetailsService {
    @Getter
    private XmlUserDetailsStore store;

    @Getter
    public final Set<String> adminUsernames = new HashSet<>();

    @Override
    public UserDetails loadUserByUsername(String username) {
        return store.findBy(username);
    }

    public XmlUserDetailsService(File file) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(XmlUserDetailsStore.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader reader = new FileReader(file);
            store = (XmlUserDetailsStore) unmarshaller.unmarshal(reader);
            System.out.println(store.getUsers());
            System.out.println("============ USERS =============");
            for (UserDetails user : store.users) {
                System.out.println("User: " + user.getUsername() + ":" + user.getPassword());
                for (GrantedAuthority authority : user.getAuthorities()) {
                    System.out.println(authority.getAuthority());
                    if (authority.getAuthority().equals("ROLE_ADMIN")) adminUsernames.add(user.getUsername());
                }
            }
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
