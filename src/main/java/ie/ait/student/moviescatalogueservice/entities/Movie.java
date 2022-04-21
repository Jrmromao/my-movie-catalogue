package ie.ait.student.moviescatalogueservice.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name="movies_tbl")
@Builder
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, nullable = false)
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String genre;
    @Column(name = "RELEASEDYEAR",
            columnDefinition = "NUMERIC", nullable = false)
    private int releasedYear;

    public Movie(String name, String genre, int releasedYear) {
        this.name = name;
        this.genre = genre;
        this.releasedYear = releasedYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", releasedYear=" + releasedYear +
                '}';
    }

}


