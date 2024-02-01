package org.bedu.movies.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bedu.movies.dto.CreateMovieDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.dto.UpdateMovieDTO;
import org.bedu.movies.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.LinkedList;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MovieControllerTest {

  @MockBean
  private MovieService service;
  
  @Autowired
  private MovieController controller;

  @Test
  @DisplayName("Controller should be injected")
  void smokeTest() {
    assertNotNull(controller);
  }

  @Test
  @DisplayName("Controller should return a list of movies")
  void findAllTest() {
    List<MovieDTO> data = new LinkedList<>();

    MovieDTO movie = new MovieDTO();

    movie.setId(199);
    movie.setTitle("Titanic");
    movie.setYear(1997);

    data.add(movie);

    when(service.findAll()).thenReturn(data);

    List<MovieDTO> result = controller.findAll();

    assertNotNull(result);
    assertTrue(result.size() > 0);
    assertEquals(movie.getId(), result.get(0).getId());
    assertEquals(movie.getTitle(), result.get(0).getTitle());
    assertEquals(movie.getYear(), result.get(0).getYear());
  }

  @Test
  @DisplayName("Controller should save a movie")
  void saveTest() {
    CreateMovieDTO dto = new CreateMovieDTO();

    dto.setTitle("Intensamente");
    dto.setYear(2014);

    MovieDTO movie = new MovieDTO();

    movie.setId(299);
    movie.setTitle(dto.getTitle());
    movie.setYear(dto.getYear());

    when(service.save(any(CreateMovieDTO.class))).thenReturn(movie);

    MovieDTO result = controller.save(dto);

    assertNotNull(result);
    assertEquals(movie.getId(), result.getId());
    assertEquals(movie.getTitle(), result.getTitle());
    assertEquals(movie.getYear(), result.getYear());
  }

  @Test
  @DisplayName("Controller should update a movie")
  void updateTest() {
    UpdateMovieDTO dto = new UpdateMovieDTO();

    dto.setTitle("T1tanic");
    dto.setYear(2008);

    controller.update(400, dto);

    // Verificando que el método update del servicio
    // haya sido ejecutado 1 vez
    verify(service, times(1)).update(400, dto);
  }

  @Test
  @DisplayName("Controller should delete a movie")
  void deleteByIdTest() {
    controller.deleteById(8793);

    verify(service, times(1)).deleteById(8793);
  }
}
