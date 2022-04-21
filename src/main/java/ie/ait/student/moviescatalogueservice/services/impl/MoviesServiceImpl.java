package ie.ait.student.moviescatalogueservice.services.impl;

import ie.ait.student.moviescatalogueservice.Dto.MovieDto;
import ie.ait.student.moviescatalogueservice.entities.Movie;
import ie.ait.student.moviescatalogueservice.repositories.MovieRepository;
import ie.ait.student.moviescatalogueservice.services.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MoviesServiceImpl implements MovieService {


    final MovieRepository movieRepository;

    public MoviesServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public void delete(Long movieId) {

        Optional<Movie> movie =  movieRepository.findById(movieId);
        if(movie.isPresent())
        movieRepository.delete(movie.get());
    }

    @Override
    public Movie update(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public boolean checkIfMovieExist(Long id) {
        return movieRepository.findById(id).isPresent();
    }


}
