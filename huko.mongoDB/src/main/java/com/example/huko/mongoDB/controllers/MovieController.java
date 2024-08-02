package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.Movie;
import com.example.huko.mongoDB.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAllMovies(@RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            var customizedResponse = new CustomizedResponse("A List of Movies", service.getMovies());
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovieById(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            CustomizedResponse customizedResponse = null;
            try {
                customizedResponse = new CustomizedResponse("Movie with id " + id, Collections.singletonList(service.getMovieById(id)));
            } catch (Exception e) {
                customizedResponse = new CustomizedResponse(e.getMessage(), null);
                return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            service.insertIntoMovies(movie);
            return new ResponseEntity(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            service.deleteMovie(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchMoviesByTitle(@RequestParam(value = "title") String title, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            var customizedResponse = new CustomizedResponse("A List of Movies/TV Shows with title containing: " + title, service.searchMoviesByTitle(title));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/featured")
    public ResponseEntity getFeaturedMovies(@RequestParam(value = "featured") String featured, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            var customizedResponse = new CustomizedResponse("A List of Featured Movies", service.getFeaturedMovies(featured));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMovie(@PathVariable("id") String id, @RequestBody Movie movie, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            CustomizedResponse customizedResponse = null;
            try {
                Optional<Movie> existingMovie = service.getMovieById(id);
                if (!existingMovie.isPresent()) {
                    customizedResponse = new CustomizedResponse("Movie with id " + id + " is not found", null);
                    return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
                }

                Movie updatedMovie = service.updateMovie(id, movie);
                customizedResponse = new CustomizedResponse("Movie with id " + id + " is updated", Collections.singletonList(updatedMovie));
                return new ResponseEntity(customizedResponse, HttpStatus.OK);
            } catch (Exception e) {
                customizedResponse = new CustomizedResponse(e.getMessage(), null);
                return new ResponseEntity(customizedResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean validateToken(String token) {
        // Validate token logic (for simplicity, check if token is an email)
        return token.contains("@");
    }
}
