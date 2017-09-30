package com.money.spier.api.infrastructure.web.errors.handlers;

import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.web.errors.ErrorState;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public final class ErrorStateResponseBodyAdvice extends ResponseEntityExceptionHandler {

  @Autowired
  private ErrorState errorState;

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, Collection<String>>> handleValidationException() {
    return new ResponseEntity<>(errorState.getErrors(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Map<String, String>> handleConflictException(Exception ex) {
    return new ResponseEntity<>(
        Collections.singletonMap("message", ex.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(Exception ex) {
    return new ResponseEntity<>(
        Collections.singletonMap("message", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

}
