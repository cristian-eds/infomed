package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GuestUserDetailsService implements UserDetailsService {

    private final PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByAccessCode(username).orElseThrow(() -> new UsernameNotFoundException("AccessCode invalid"));
        return new User(person.getAccessCode(),"", Collections.singletonList(new SimpleGrantedAuthority(Role.GUEST.toString())));
    }
}
