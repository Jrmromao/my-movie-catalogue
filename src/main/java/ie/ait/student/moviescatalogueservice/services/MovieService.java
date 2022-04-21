package ie.ait.student.moviescatalogueservice.services;

import ie.ait.student.moviescatalogueservice.Dto.MovieDto;
import ie.ait.student.moviescatalogueservice.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {
    Movie save(Movie movie);
    Page<Movie> findAll(Pageable pageable);
    Optional<Movie> findById(Long id);
    void delete(Long movieId);
    Movie update(Movie movie);
    boolean checkIfMovieExist(Long id);
}
