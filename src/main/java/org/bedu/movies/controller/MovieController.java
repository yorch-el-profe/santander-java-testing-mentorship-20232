package org.bedu.movies.controller;

import org.bedu.movies.dto.CreateMovieDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.dto.UpdateMovieDTO;
import org.bedu.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
  
  private MovieService service;

  @Autowired
  public MovieController(MovieService service) {
    this.service = service;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<MovieDTO> findAll() {
    return service.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MovieDTO save(@Valid @RequestBody CreateMovieDTO dto) {
    return service.save(dto);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@Param("id") long id, @Valid @RequestBody UpdateMovieDTO dto) throws Exception {
    service.update(id, dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@Param("id") long id) {
    service.deleteById(id);
  }
}
