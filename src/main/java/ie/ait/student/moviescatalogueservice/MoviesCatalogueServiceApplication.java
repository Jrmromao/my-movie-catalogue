package ie.ait.student.moviescatalogueservice;

import ie.ait.student.moviescatalogueservice.entities.Movie;
import ie.ait.student.moviescatalogueservice.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MoviesCatalogueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesCatalogueServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner sampleDara(MovieRepository repository) {
//        return (args -> {
//            repository.save(new Movie("American Pie", "Comedy", 1999));
//            repository.save(new Movie("The Hangover", "Comedy", 2009));
//        });
//    }

}
