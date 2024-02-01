package org.bedu.movies.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  
  private String code;

  public ApiException(String code, String message) {
    super(message);
    this.code = code;
  }
}
