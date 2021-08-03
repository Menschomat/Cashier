package de.menschomat.cashier.database.security;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JPAUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public JPAUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DBUser> dbUserOptional = repository.findById(username);
        if (dbUserOptional.isPresent()) {
            DBUser user = dbUserOptional.get();
            if (user.getRole() == null) {
                user.setRole("USER");
            }
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            return new User(user.getId(), user.getPassword(), authorities);
        } else
            throw new UsernameNotFoundException("DBUser not found");
    }
}