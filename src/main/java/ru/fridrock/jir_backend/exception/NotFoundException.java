package ru.fridrock.jir_backend.exception;


public class NotFoundException extends RuntimeException{
  public NotFoundException(String message){
    super(message);
  }
}
