package org.bedu.movies.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMovieDTO {
  
  @NotBlank(message = "El titulo es obligatorio")
  private String title;

  @NotBlank(message = "El año es obligatorio")
  @Min(value = 1910, message = "El año debe ser mayor a 1910")
  private Integer year;
}
