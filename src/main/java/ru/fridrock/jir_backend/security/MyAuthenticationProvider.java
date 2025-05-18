package ru.fridrock.jir_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.fridrock.jir_backend.utils.jwt.JwtTokenUtils;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {
  private final JwtTokenUtils jwtTokenUtils;
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    MyAuthentication myAuthentication = (MyAuthentication) authentication;
    try{
      Claims claims = jwtTokenUtils.parseToken(myAuthentication.getToken());
      if (claims.getSubject()!=null){
        myAuthentication.setUserId(UUID.fromString(claims.get("userId")
            .toString()));
        myAuthentication.setUsername(claims.getSubject());
        myAuthentication.setAuthenticated(true);
      }
    }catch(JwtException exception){
      myAuthentication.setAuthenticated(false);
    }
    return myAuthentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(MyAuthentication.class);
  }
}
