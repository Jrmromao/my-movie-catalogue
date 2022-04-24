package ie.ait.student.moviescatalogueservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Hello from index controller";
    }
}
