package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.Show;
import com.example.huko.mongoDB.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService service;

    @Autowired
    public ShowController(ShowService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAllShows() {
        var customizedResponse = new CustomizedResponse("A List of Shows", service.getShows());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getShowById(@PathVariable("id") String id) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Show with id " + id, Collections.singletonList(service.getShowById(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addShow(@RequestBody Show show) {
        service.insertIntoShows(show);
        return new ResponseEntity(show, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteShow(@PathVariable("id") String id) {
        service.deleteShow(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchShowsByTitle(@RequestParam(value = "title") String title) {
        var customizedResponse = new CustomizedResponse("A List of Shows with title containing: " + title, service.searchShowsByTitle(title));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/featured")
    public ResponseEntity getFeaturedShows(@RequestParam(value = "featured") String featured) {
        var customizedResponse = new CustomizedResponse("A List of Featured Shows", service.getFeaturedShows(featured));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateShow(@PathVariable("id") String id, @RequestBody Show show) {
        CustomizedResponse customizedResponse = null;
        try {
            Optional<Show> existingShow = service.getShowById(id);
            if (!existingShow.isPresent()) {
                customizedResponse = new CustomizedResponse("Show with id " + id + " is not found", null);
                return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
            }

            Show updatedShow = service.updateShow(id, show);
            customizedResponse = new CustomizedResponse("Show with id " + id + " is updated", Collections.singletonList(updatedShow));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
