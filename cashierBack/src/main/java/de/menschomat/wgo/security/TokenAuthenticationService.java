package de.menschomat.wgo.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenAuthenticationService {

    private static final long EXPIRATIONTIME = 864_000_000; // 10 days

    private static final String SECRET = "ThisIsASecret";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";
    private static final String AUTHORITIES_KEY = "ROLES";

    static void addAuthentication(HttpServletResponse res, Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String JWT = Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) throws BadCredentialsException {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String user;
            final Collection authorities;
            try {
                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
                user = claimsJws.getBody().getSubject();
                final Claims claims = claimsJws.getBody();
                authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token");
            }

            // parse the token.


        }
        return null;
    }

}