package org.bedu.movies.dto;

import lombok.Data;

@Data
public class ErrorDTO {
  
  private String code;
  private String message;
  private Object details;
}
