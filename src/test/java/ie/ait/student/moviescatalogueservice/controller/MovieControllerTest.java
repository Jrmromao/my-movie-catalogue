package ie.ait.student.moviescatalogueservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.ait.student.moviescatalogueservice.Dto.MovieDto;
import ie.ait.student.moviescatalogueservice.entities.Movie;
import ie.ait.student.moviescatalogueservice.services.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    // Junit test for get all the movies
    @Test
    public void givenAMovieList_whenGrtAll_thenReturnMovieList() throws Exception {
        // given - precondition or setup
        Movie movie1 = Movie.builder().name("Limitless").genre("Action").releasedYear(2009).build();
        Movie movie2 = Movie.builder().name("Limitless II").genre("Action").releasedYear(2019).build();
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        var movieList = Arrays.asList(movie1, movie2);
        var moviePage = new PageImpl<>(movieList);

        given(movieService.findAll(captor.capture())).willReturn(moviePage);
        // when  - action or the Behaviour we're testing
        ResultActions response = mockMvc.perform(get("/api/movies"));
        // then  - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(movieList.size())));
    }

    // Junit test for creating new movie
    @Test
    public void givenMovieObject_whenCreateMovie_thenReturnMovieObkect() throws Exception {
        // given - precondition or setup
        MovieDto movieDto = MovieDto.builder().name("Limitless").genre("Action").releasedYear(2009).build();
        given(movieService.save(any(Movie.class))).willAnswer(invocation -> invocation.getArgument(0));
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(post("/api/movie").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)));
        // then  - verify the output
        resultActions.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(movieDto.getName())))
                .andExpect(jsonPath("$.genre", is(movieDto.getGenre())))
                .andExpect(jsonPath("$.releasedYear", is(movieDto.getReleasedYear())));
    }

    // Junit test for find movie by id - positive scenario
    @Test
    public void givenMovieId_whenFindMovieById_thenReturnMovieObject() throws Exception {
        // given - precondition or setup
        Long movieId = 2L;
        Movie movie = Movie.builder().name("Limitless").genre("Action").releasedYear(2009).build();
        given(movieService.findById(movieId)).willReturn(Optional.of(movie));
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(get("/api/movie/{id}", movieId));
        // then  - verify the output
        resultActions.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.genre", is(movie.getGenre())))
                .andExpect(jsonPath("$.releasedYear", is(movie.getReleasedYear())));
    }

    // Junit test for find movie by id - negative scenario
    @Test
    public void givenInvalidMovieId_whenFindMovieById_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        Long movieId = 2L;
        Movie movie = Movie.builder().name("Limitless").genre("Action").releasedYear(2009).build();
        given(movieService.findById(movieId)).willReturn(Optional.empty());
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(get("/api/movie/{id}", movieId));
        // then  - verify the output
        resultActions.andDo(print()).andExpect(status().isNotFound());
    }

    // Junit test for updating movie - positive scenario
    @Test
    public void givenUpdateMovie_whenUpdateMovie_thenReturnMovieObject() throws Exception {
        // given - precondition or setup
        Long movieId = 1L;
        Movie savedMovie = Movie.builder().name("Limitless").genre("Action").releasedYear(2009).build();
        Movie updatedMovie = Movie.builder().name("Limitless II").genre("Action").releasedYear(2019).build();
        given(movieService.findById(movieId)).willReturn(Optional.of(savedMovie));
        given(movieService.update(any(Movie.class))).willAnswer(invocation -> invocation.getArgument(0));
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(put("/api/movie/{id}", movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMovie)));
        // then  - verify the output
        resultActions.andDo(print())
                .andExpect(jsonPath("$.name", is(updatedMovie.getName())))
                .andExpect(jsonPath("$.genre", is(updatedMovie.getGenre())))
                .andExpect(jsonPath("$.releasedYear", is(updatedMovie.getReleasedYear())));

    }

    // Junit test for updating movie - negative scenario
    @Test
    public void givenInvalidMovie_whenUpdateMovie_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        Long movieId = 1L;
        Movie updatedMovie = Movie.builder().name("Limitless II").genre("Action").releasedYear(2019).build();
        given(movieService.findById(movieId)).willReturn(Optional.empty());
        given(movieService.update(any(Movie.class))).willAnswer(invocation -> invocation.getArgument(0));
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(put("/api/movie/{id}", movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMovie)));
        // then  - verify the output
        resultActions.andDo(print())
                .andExpect(status().isNotFound());

    }

    // Junit test for deleting movie
    @Test
    public void givenValidMovie_whenDeleteMovie_thenReturnStatusCode200() throws Exception {
        // given - precondition or setup
        Long movieId =1L;
        willDoNothing().given(movieService).delete(movieId);
        // when  - action or the Behaviour we're testing
        ResultActions resultActions = mockMvc.perform(delete("/api/movie/{id}", movieId));
        // then  - verify the output
        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

}
