package de.menschomat.cashier.security;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, BadCredentialsException {

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
        if (user == null) {
            throw new BadCredentialsException("Invalid EmailId/password");
        } else {
            if (user.getRole() == null) {
                user.setRole("USER");
            }
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        try {

            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getId(), password, authorities));
        } catch (BadCredentialsException e) {
            logger.debug("BAD_CREDENTIALS - Call rejected - Not permitted to enter!");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "BAD_CREDENTIALS");
        }
        return null;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        logger.debug("LOGIN_ACTION:" + authResult.getName());
        TokenAuthenticationService.addAuthentication(response, authResult);
    }


}