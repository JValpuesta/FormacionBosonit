/*package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.PersonaService;
import com.bosonit.block7crudvalidation.application.PersonaServiceImp2;
import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(BookController.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonaControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    private static final int id = 1;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    @MockBean
    private PersonaRepository mockRepository;

    @Mock
    @MockBean
    private PersonaServiceImp2 mockService;

    @Before
    public void init() {
        Persona persona = new Persona( new PersonaInputDto("lolito", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date()));
        when(mockRepository.findById(id)).thenReturn(Optional.of(persona));
        when(mockService.getPersonaById(id)).thenReturn(persona.personaToPersonaOutputDto());
    }

    @Test
    public void testGetPersonaById() throws Exception {

        mockMvc.perform(get("/persona/id/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)));

        verify(mockService, times(1)).getPersonaById(id);

    }

    @Test
    public void find_allBook_OK() throws Exception {

        List<Book> books = Arrays.asList(
                new Book(1L, "Book A", "Ah Pig", new BigDecimal("1.99")),
                new Book(2L, "Book B", "Ah Dog", new BigDecimal("2.99")));

        when(mockRepository.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Book A")))
                .andExpect(jsonPath("$[0].author", is("Ah Pig")))
                .andExpect(jsonPath("$[0].price", is(1.99)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Book B")))
                .andExpect(jsonPath("$[1].author", is("Ah Dog")))
                .andExpect(jsonPath("$[1].price", is(2.99)));

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    public void find_bookIdNotFound_404() throws Exception {
        mockMvc.perform(get("/books/5")).andExpect(status().isNotFound());
    }

    @Test
    public void save_book_OK() throws Exception {

        Book newBook = new Book(1L, "Spring Boot Guide", "mkyong", new BigDecimal("2.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);

        mockMvc.perform(post("/books")
                        .content(om.writeValueAsString(newBook))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.author", is("mkyong")))
                .andExpect(jsonPath("$.price", is(2.99)));

        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void update_book_OK() throws Exception {

        Book updateBook = new Book(1L, "ABC", "mkyong", new BigDecimal("19.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(updateBook);

        mockMvc.perform(put("/books/1")
                        .content(om.writeValueAsString(updateBook))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ABC")))
                .andExpect(jsonPath("$.author", is("mkyong")))
                .andExpect(jsonPath("$.price", is(19.99)));


    }

    @Test
    public void patch_bookAuthor_OK() throws Exception {

        when(mockRepository.save(any(Book.class))).thenReturn(new Book());
        String patchInJson = "{\"author\":\"ultraman\"}";

        mockMvc.perform(patch("/books/1")
                        .content(patchInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void patch_bookPrice_405() throws Exception {

        String patchInJson = "{\"price\":\"99.99\"}";

        mockMvc.perform(patch("/books/1")
                        .content(patchInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(0)).save(any(Book.class));
    }

    @Test
    public void delete_book_OK() throws Exception {

        doNothing().when(mockRepository).deleteById(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).deleteById(1L);
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}*/
