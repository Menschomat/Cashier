package de.menschomat.cashier.database.security;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class JPAUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public JPAUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DBUser user = repository.findByUsername(username);
        DBUser user = repository.findById(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("DBUser not found");
        }
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new User(user.getId(), user.getPassword(), authorities);
    }
}