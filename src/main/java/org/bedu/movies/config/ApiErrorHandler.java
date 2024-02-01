package org.bedu.movies.config;

import org.bedu.movies.dto.ErrorDTO;
import org.bedu.movies.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.LinkedList;

@RestControllerAdvice
public class ApiErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO validationErrors(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<String> errors = new LinkedList<>();

    for (FieldError fieldError : fieldErrors) {
      errors.add(fieldError.getDefaultMessage());
    }

    ErrorDTO error = new ErrorDTO();

    error.setCode("ERR_VALID");
    error.setMessage("Hubo un error al validar los datos de entrada");
    error.setDetails(errors);

    return error;
  }

  @ExceptionHandler(ApiException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ErrorDTO apiErrors(ApiException ex) {

    ErrorDTO error = new ErrorDTO();

    error.setCode(ex.getCode());
    error.setMessage(ex.getMessage());

    return error;
  }
}
