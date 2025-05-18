package ru.fridrock.jir_backend.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.user.dto.AuthRequest;
import ru.fridrock.jir_backend.user.dto.AuthResponse;
import ru.fridrock.jir_backend.user.dto.RegisterRequest;
import ru.fridrock.jir_backend.utils.jwt.JwtTokenUtils;
import ru.fridrock.jir_backend.utils.jwt.UserToken;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
  private final JwtTokenUtils jwtTokenUtils;
  private final UserService userService;

  @PostMapping("/reg")
  public AuthResponse register(@RequestBody RegisterRequest dto) {
    UserToken userToken = userService.createUser(dto);
    log.info("create user with userId:" + userToken.userId().toString());
    return new AuthResponse(jwtTokenUtils.generateToken(userToken));

  }

  @PostMapping("/auth")
  public AuthResponse auth(@RequestBody AuthRequest dto) {
    UserToken userToken = userService.authenticateUser(dto);
    return new AuthResponse(jwtTokenUtils.generateToken(userToken));
  }
}
