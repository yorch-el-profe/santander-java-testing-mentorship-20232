package org.bedu.movies.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateMovieDTO {
  private String title;

  @Min(value = 1910, message = "El año debe ser mayor a 1910")
  private Integer year;
}
