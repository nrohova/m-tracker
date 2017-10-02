package com.money.spier.api.infrastructure.web.errors.handlers;

import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public final class ErrorStateResponseBodyAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Map<String, String>> handleConflictException(ConflictException ex) {
    return new ResponseEntity<>(
        Collections.singletonMap("message", ex.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(
        Collections.singletonMap("message", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
    return new ResponseEntity<>(
        Collections.singletonMap("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField,
            DefaultMessageSourceResolvable::getDefaultMessage));
    return new ResponseEntity<>(errors, headers, status);
  }
}
