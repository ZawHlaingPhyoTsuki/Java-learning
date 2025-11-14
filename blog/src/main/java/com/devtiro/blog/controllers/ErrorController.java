package com.devtiro.blog.controllers;

import com.devtiro.blog.domain.dtos.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {

    List<ApiErrorResponse.FieldError> fieldErrors = new ArrayList<>();

    // Extract field-level validation errors
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.add(ApiErrorResponse.FieldError.builder()
                                                 .field(fieldError.getField())
                                                 .error(
                                                     fieldError.getDefaultMessage())
                                                 .build());
    }

    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.BAD_REQUEST.value())
                                             .message("Validation failed")
                                             .errors(fieldErrors)
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
    log.error("Caught exception", ex);

    String message = "An unexpected error occurred";

    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.INTERNAL_SERVER_ERROR.value())
                                             .message(message)
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.BAD_REQUEST.value())
                                             .message(ex.getMessage())
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiErrorResponse> handleIllegalStateException(
      IllegalArgumentException ex) {
    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.CONFLICT.value())
                                             .message(ex.getMessage())
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(
      BadCredentialsException ex) {
    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.UNAUTHORIZED.value())
//                                             .message(ex.getMessage())
                                             .message("Invalid credentials")
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    ApiErrorResponse error = ApiErrorResponse.builder()
                                             .status(
                                                 HttpStatus.NOT_FOUND.value())
                                             .message(ex.getMessage())
                                             .build();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
