package org.bedu.movies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bedu.movies.dto.CreateMovieDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.dto.UpdateMovieDTO;
import org.bedu.movies.exception.MovieNotFoundException;
import org.bedu.movies.model.Movie;
import org.bedu.movies.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MovieServiceTest {
  
  @MockBean
  private MovieRepository repository;

  @Autowired
  private MovieService service;

  @Test
  @DisplayName("Service should be injected")
  void smokeTest() {
    assertNotNull(service);
  }

  @Test
  @DisplayName("Service should return movies from repository")
  void findAllTest() {
    List<Movie> data = new LinkedList<>();

    Movie movie = new Movie();

    movie.setId(6548);
    movie.setTitle("Super Mario The Movie");
    movie.setYear(2023);

    data.add(movie);

    when(repository.findAll()).thenReturn(data);

    List<MovieDTO> result = service.findAll();

    assertNotNull(result);
    assertTrue(result.size() > 0);
    assertEquals(movie.getId(), result.get(0).getId());
    assertEquals(movie.getTitle(), result.get(0).getTitle());
    assertEquals(movie.getYear(), result.get(0).getYear());
  }

  @Test
  @DisplayName("Service should save a movie in repository")
  void saveTest() {
    CreateMovieDTO dto = new CreateMovieDTO();

    dto.setTitle("Five Nights at Freddys");
    dto.setYear(2023);

    Movie model = new Movie();

    model.setId(8794);
    model.setTitle(dto.getTitle());
    model.setYear((dto.getYear()));

    when(repository.save(any(Movie.class))).thenReturn(model);

    MovieDTO result = service.save(dto);

    assertNotNull(result);
    assertEquals(model.getId(), result.getId());
    assertEquals(model.getTitle(), result.getTitle());
    assertEquals(model.getYear(), result.getYear());
  }

  @Test
  @DisplayName("Service should throws an error if movie was not found")
  void updateWithErrorTest() {
    UpdateMovieDTO dto = new UpdateMovieDTO();
    Optional<Movie> dummy = Optional.empty();

    when(repository.findById(anyLong())).thenReturn(dummy);

    assertThrows(MovieNotFoundException.class, () -> service.update(100, dto));
  }

  @Test
  @DisplayName("Service should update a movie in repository")
  void updateTest() {
    UpdateMovieDTO dto = new UpdateMovieDTO();

    dto.setTitle("Avengers Endgame");
    dto.setYear(2019);

    Movie movie = new Movie();

    movie.setId(2874);
    movie.setTitle("Av3g3rs 3ndg4m3");
    movie.setYear(2050);

    when(repository.findById(anyLong())).thenReturn(Optional.of(movie));

    service.update(2874, dto);

    assertEquals(dto.getTitle(), movie.getTitle());
    assertEquals(dto.getYear(), movie.getYear());
    verify(repository, times(1)).save(movie);
  }

  @Test
  @DisplayName("Service should delete a movie by id in repository")
  void deleteByIdTest() {
    service.deleteById(38798l);

    verify(repository, times(1)).deleteById(38798l);
  }
}
