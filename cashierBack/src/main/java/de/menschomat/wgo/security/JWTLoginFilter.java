package de.menschomat.wgo.security;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    UserRepository userRepository;

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = "";
        String password = "";
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            username = request.getParameter("username");
            password = request.getParameter("password");
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> json = new Gson().fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), mapType);
            json.get("username");
            json.get("password");
            username = json.get("username");
            password = json.get("password");
        }
        DBUser user = userRepository.findByUsername(username);
        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", user.id, password);
        if (user.role == null) {
            user.role = "ROLE_USER";
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.role));
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(user.id, password, authorities));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        // Write Authorization to Headers of Response.
        TokenAuthenticationService.addAuthentication(response, authResult);

        String authorizationString = response.getHeader("Authorization");

        System.out.println("Authorization String=" + authorizationString);
    }

}