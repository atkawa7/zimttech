package com.example.demo.security;

import com.example.demo.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;

public class TokenHeaderAuthentication extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String token;
    private User user;

    public TokenHeaderAuthentication(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    public TokenHeaderAuthentication(User user) {
        super(of(user.getRole()).map(c ->new  SimpleGrantedAuthority(c.name())).collect(Collectors.toList()));
        this.user = user;
        this.token = user.getToken();
        setAuthenticated(true);
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials(){
        return token;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }



}
