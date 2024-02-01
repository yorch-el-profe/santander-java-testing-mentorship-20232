package org.bedu.movies.repository;

import org.bedu.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  // SELECT * FROM movies WHERE year = :year
  List<Movie> findByYear(int year);

  // SELECT * FROM movies WHERE title LIKE :title
  List<Movie> findByTitleContaining(String title);
}
