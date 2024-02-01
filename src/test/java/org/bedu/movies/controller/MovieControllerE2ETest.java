package org.bedu.movies.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bedu.movies.dto.ErrorDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.model.Movie;
import org.bedu.movies.repository.MovieRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MovieControllerE2ETest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MovieRepository repository;

  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("GET /movies should return an empty list")
  void emptyListTest() throws Exception {

    // Realizar una petición de tipo GET
    // hacia /movies y esperar que el resultado sea 200
    MvcResult result = mockMvc.perform(get("/movies"))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    assertEquals("[]", content);
  }

  @Test
  @DisplayName("GET /movies should return a list of movies")
  void findAllTest() throws Exception {
    Movie movie1 = new Movie();
    Movie movie2 = new Movie();

    movie1.setTitle("El Gato Con Botas 2");
    movie1.setYear(2022);

    movie2.setTitle("Shrek");
    movie2.setYear(2001);

   repository.save(movie1);
   repository.save(movie2);

    MvcResult result = mockMvc.perform(get("/movies"))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    // Creamos una referencia del tipo al que se va a convertir el JSON
    TypeReference<List<MovieDTO>> listTypeReference = new TypeReference<List<MovieDTO>>() {};

    // Convertimos el JSON a un objeto de Java
    List<MovieDTO> response = mapper.readValue(content, listTypeReference);

    // Hacemos las verificaciones basadas en los objetos
    assertTrue(response.size() == 2);
    assertEquals(movie1.getTitle(), response.get(0).getTitle());
    assertEquals(movie2.getTitle(), response.get(1).getTitle());
  }

  @Test
  @DisplayName("POST /movies should return an error if title is missing")
  void titleMissingInRequestBodyTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/movies").contentType("application/json").content("{\"year\":2010}"))
        .andExpect(status().isBadRequest())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    // { "code": "ERR_VALID", "message": "Hubo un error al validar los datos de
    // entrada", "details": ["El titulo es obligatorio"]}

    String expectedResponse = "{\"code\":\"ERR_VALID\",\"message\":\"Hubo un error al validar los datos de entrada\",\"details\":[\"El titulo es obligatorio\"]}";

    assertEquals(expectedResponse, content);
  }

  @Test
  @DisplayName("POST /movies should return an error if year is missing")
  void yearMissingInRequestBodyTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/movies").contentType("application/json").content("{\"title\":\"Mi pobre angelito\"}"))
        .andExpect(status().isBadRequest())
        .andReturn();

    // Leemos el contenido con caracteres de acentos y ñ
    String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    // Convertimos el JSON a un objeto de Java
    ErrorDTO response = mapper.readValue(content, ErrorDTO.class);

    assertEquals("ERR_VALID", response.getCode());
    assertEquals("Hubo un error al validar los datos de entrada", response.getMessage());
  
    List<String> details = (List<String>) response.getDetails();

    assertEquals("El año es obligatorio", details.get(0));
  }
}
