package org.bedu.movies.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bedu.movies.model.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MovieRepositoryTest {
  
  @Autowired
  private MovieRepository repository;

  @Autowired
  private TestEntityManager manager;

  @Test
  @DisplayName("Repository should be injected")
  void smokeTest() {
    assertNotNull(repository);
  }

  @Test
  @DisplayName("Repository should filter movies by year")
  void findByYearTest() {

    Movie movie1 = new Movie();
    Movie movie2 = new Movie();
    Movie movie3 = new Movie();

    movie1.setTitle("Toy Story 3");
    movie1.setYear(2010);

    movie2.setTitle("Como entrenar a tu dragon");
    movie2.setYear(2010);

    movie3.setTitle("Scary Movie");
    movie3.setYear(2000);

    // Crea los registros en la base de datos de prueba (h2)
    manager.persist(movie1);
    manager.persist(movie2);
    manager.persist(movie3);

    List<Movie> result = repository.findByYear(2010);

    assertTrue(result.size() == 2);
  }
}
