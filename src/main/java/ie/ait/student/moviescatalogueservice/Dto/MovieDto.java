package ie.ait.student.moviescatalogueservice.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDto {

    @NotBlank
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String genre;
    @NotBlank
    private int releasedYear;

}
