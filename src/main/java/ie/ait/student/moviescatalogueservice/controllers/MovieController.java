package ie.ait.student.moviescatalogueservice.controllers;


import ie.ait.student.moviescatalogueservice.Dto.MovieDto;
import ie.ait.student.moviescatalogueservice.entities.Movie;
import ie.ait.student.moviescatalogueservice.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class MovieController {
    Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
        logger.debug("Starting: " + this.getClass());
    }

    @PostMapping(path = "/movie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody @Valid MovieDto movieDto) {
        var movie = new Movie();
        BeanUtils.copyProperties(movieDto, movie);
        Movie saveMovie = movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMovie);
    }

    @GetMapping(path = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Movie>> get(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll(pageable));
    }

    @GetMapping(path = "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> find(@PathVariable("id") Long id) {
        Optional<Movie> movie = movieService.findById(id);
        return movie.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK)
                        .body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Movie not found!"));
    }

    @PutMapping(path = "/movie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> update(@RequestBody MovieDto movie, @PathVariable("id") Long id) {

        return movieService.findById(id)
                .map(mov -> {
                    mov.setReleasedYear(movie.getReleasedYear());
                    mov.setGenre(movie.getGenre());
                    mov.setName(movie.getName());
                    Movie newMovie = movieService.update(mov);
                    return new ResponseEntity<>(newMovie, HttpStatus.OK);
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/movie/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully!");
    }
}