package de.menschomat.wgo.database.security;

import de.menschomat.wgo.database.mongo.model.DBUser;
import de.menschomat.wgo.database.mongo.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public MongoUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DBUser user = repository.findByUsername(username);
        DBUser user = repository.findById(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("DBUser not found");
        }
        if (user.role == null) {
            user.role = "USER";
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.role));
        return new User(user.id, user.password, authorities);
    }
}