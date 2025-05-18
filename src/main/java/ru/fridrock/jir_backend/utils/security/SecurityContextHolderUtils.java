package ru.fridrock.jir_backend.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.fridrock.jir_backend.security.MyAuthentication;

public class SecurityContextHolderUtils {
  public static MyAuthentication getUser() {
    return (MyAuthentication) SecurityContextHolder.getContext()
        .getAuthentication();
  }
}
