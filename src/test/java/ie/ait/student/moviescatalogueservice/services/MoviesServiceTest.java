package ie.ait.student.moviescatalogueservice.services;

import ie.ait.student.moviescatalogueservice.Dto.MovieDto;
import ie.ait.student.moviescatalogueservice.entities.Movie;
import ie.ait.student.moviescatalogueservice.repositories.MovieRepository;
import ie.ait.student.moviescatalogueservice.services.impl.MoviesServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MoviesServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MoviesServiceImpl movieService;
    private Movie movie;

    @BeforeEach
    public void setup() {
//        employeeRepository = mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        movie = Movie.builder()
                .id(1L)
                .name("Superman")
                .genre("Drama")
                .releasedYear(2012)
                .build();
    }

    // Junit test for getting all the movies
    @DisplayName("Junit test for getting all the Movies")
    @Test
    public void givenMovieList_whenFindAll_thenReturnMovieList() {
        //given - precondition and setup
        //stub of the getAll from
        var movie1 = Movie.builder()
                .name("Ironman")
                .genre("Drama")
                .releasedYear(2012)
                .build();
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        var movieList = Arrays.asList(movie1, movie);
        var moviePage = new PageImpl<>(movieList);
        BDDMockito.given(movieRepository.findAll(captor.capture())).willReturn(moviePage);
        // when  - action or the Behaviour that we're going to test
        var movies = movieService.findAll(captor.capture());
        // then  - verify or output
        Assertions.assertThat(movies).isNotNull();
        Assertions.assertThat(movies.getContent().size()).isEqualTo(2);
    }

    // Junit test for getting all the movies
    @DisplayName("Junit test for getting all the movies (negative scenario)")
    @Test
    public void givenEmptyMovieList_whenFindAll_thenReturnEmptyMovieList() {
        // given - precondition and setup
        //stub of the getAll from
        var captor = ArgumentCaptor.forClass(Pageable.class);
        var movieList = Collections.EMPTY_LIST;
        var moviePage = new PageImpl<>(movieList);
        BDDMockito.given(movieRepository.findAll(captor.capture())).willReturn(moviePage);
        // when  - action or the Behaviour that we're going to test
        var movies = movieService.findAll(captor.capture());
        // then  - verify or output
        Assertions.assertThat(movies.getContent()).isEmpty();
        Assertions.assertThat(movies.getContent().size()).isEqualTo(0);
    }

    // Junit test for get movie by id
    @DisplayName("Junit test for get movie by id")
    @Test
    public void givenMovieId_whenFindMovieByID_thenReturnMovieObject() {
        // given - precondition and setup
        BDDMockito.given(movieRepository.findById(movie.getId())).willReturn(Optional.of(movie));
        // when  - action or the Behaviour that we're going to test
        var savedEmployee = movieService.findById(movie.getId()).get();
        // then  - verify or output
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // Junit test for update movie
    @DisplayName("Junit test for update movie")
    @Test
    public void givenMovieObject_whenUpdateMovie_thenReturnMovie() {
        // given - precondition and setup
        BDDMockito.given(movieRepository.save(movie)).willReturn(movie);
        movie.setGenre("Comedy");
        movie.setName("Scary Movie");
        // when  - action or the Behaviour that we're going to test
        var updatedMovie = movieService.update(movie);
        // then  - verify or output
        Assertions.assertThat(updatedMovie.getGenre()).isEqualTo("Comedy");
        Assertions.assertThat(updatedMovie.getName()).isEqualTo("Scary Movie");
    }

    // Junit test for delete movie operation
    @DisplayName("Junit test for delete movie operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmpObject() {
        // given - precondition and setup
        movieRepository.save(movie);
        // when  - action or the Behaviour that we're going to test
        movieRepository.delete(movie);
        var employeeOptional = movieService.findById(movie.getId());
        // then  - verify or output
        assertThat(employeeOptional).isEmpty();
    }



}