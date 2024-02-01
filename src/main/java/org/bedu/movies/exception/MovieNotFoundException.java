package org.bedu.movies.exception;

public class MovieNotFoundException extends ApiException {
  
  public MovieNotFoundException(long id) {
    super("ERR_MOVIE_NOT_FOUND", "No se encontro la pelicula con id: " + id);
  }
}
