package ie.ait.student.moviescatalogueservice.repositories;

import ie.ait.student.moviescatalogueservice.entities.Movie;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
