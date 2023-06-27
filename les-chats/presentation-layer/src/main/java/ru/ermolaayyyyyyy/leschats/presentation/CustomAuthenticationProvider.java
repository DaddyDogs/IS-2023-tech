package ru.ermolaayyyyyyy.leschats.presentation;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ermolaayyyyyyy.leschats.entities.User;

@Component
public class CustomAuthenticationProvider
        implements AuthenticationProvider {
    @Autowired
    AmqpTemplate template;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = (User) template.convertSendAndReceive("authenticate", authentication);

        if (user == null) {
            throw new BadCredentialsException("Incorrect user credentials");
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
