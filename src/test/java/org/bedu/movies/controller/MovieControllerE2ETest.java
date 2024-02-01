package org.bedu.movies.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MovieControllerE2ETest {

  @Autowired
  private MockMvc mockMvc;

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
}
