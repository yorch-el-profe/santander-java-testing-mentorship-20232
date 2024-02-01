package org.bedu.movies.service;

import org.bedu.movies.dto.CreateMovieDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.dto.UpdateMovieDTO;
import org.bedu.movies.exception.MovieNotFoundException;
import org.bedu.movies.mapper.MovieMapper;
import org.bedu.movies.model.Movie;
import org.bedu.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
  
  private MovieRepository repository;

  private MovieMapper mapper;

  @Autowired
  public MovieService(MovieRepository repository, MovieMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<MovieDTO> findAll() {
    List<Movie> data = repository.findAll();
    return mapper.toDTO(data);
  }

  public MovieDTO save(CreateMovieDTO dto) {
    Movie result = repository.save(mapper.toModel(dto));
    return mapper.toDTO(result);
  }

  public void update(long id, UpdateMovieDTO dto) throws Exception {
    Optional<Movie> result = repository.findById(id);

    if (result.isEmpty()) {
      throw new MovieNotFoundException(id);
    }

    Movie model = result.get();

    mapper.updateModel(dto, model);

    repository.save(model);
  }

  public void deleteById(long id) {
    repository.deleteById(id);
  }
}
