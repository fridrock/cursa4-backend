package ru.fridrock.jir_backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomProblemDetails> handleException(
      Exception ex,
      HttpServletRequest request) {
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("internal error")
        .details(ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }


  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<CustomProblemDetails> handleUnauthorizedException(
      UnauthorizedException ex,
      HttpServletRequest request) {
    int status = HttpStatus.UNAUTHORIZED.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Error authorizing")
        .details(ex.getMessage())
        .path(request.getMethod() + " " +request.getRequestURI())
        .build();
    return ResponseEntity.status(status).body(customProblemDetails);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomProblemDetails> handleNotFoundException(
      NotFoundException ex,
      HttpServletRequest request) {
    int status = HttpStatus.NOT_FOUND.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Not found")
        .details(ex.getMessage())
        .path(request.getMethod() + " " +request.getRequestURI())
        .build();
    return ResponseEntity.status(status).body(customProblemDetails);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<CustomProblemDetails> handleUserAlreadyExist(
      UserAlreadyExistException ex,
      HttpServletRequest request) {
    int status = HttpStatus.CONFLICT.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("User already exist")
        .details(ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }

  @ExceptionHandler(AiGenerationException.class)
  public ResponseEntity<CustomProblemDetails> handleAiGenerationException(
      AiGenerationException ex,
      HttpServletRequest request) {
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Failed to create tasks with AI")
        .details(ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomProblemDetails> handleValidationExceptions(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    int status = HttpStatus.BAD_REQUEST.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Validation failed")
        .details(errors.toString())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<CustomProblemDetails> handleConstraintViolationException(
      ConstraintViolationException ex,
      HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessage = violation.getMessage();
      errors.put(fieldName, errorMessage);
    });

    int status = HttpStatus.BAD_REQUEST.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Validation failed")
        .details(errors.toString())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<CustomProblemDetails> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex,
      HttpServletRequest request) {
    int status = HttpStatus.BAD_REQUEST.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Invalid request format")
        .details("Failed to parse request body: " + ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }
}
