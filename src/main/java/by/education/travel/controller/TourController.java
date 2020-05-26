package by.education.travel.controller;

import by.education.travel.entity.Tour;
import by.education.travel.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tour/")
@Slf4j
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable("id") int id) {
        Tour tour = tourService.getTourById(id);
        log.info("In getTourById {} tour: {}", id, tour);
        if (tour == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(tour, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        log.info("In getAllTours find {}", tours);
        if (tours == null || tours.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(tours, OK);
    }

    @PostMapping("")
    public ResponseEntity<Tour> saveTour(@RequestBody Tour tour) {
        log.info("In saveTour {}", tour);
        if (tour == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        tourService.saveTour(tour);
        return new ResponseEntity<>(tour, CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Tour> deleteTour(@PathVariable("id") int id) {
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        tourService.deleteTour(tour);
        log.info("In deleteTour {} successfully deleted", tour);
        return new ResponseEntity<>(tour, NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<Tour> updateTour(@RequestBody Tour tour) {
        if (tour == null || tourService.getTourById(tour.getId()) == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        tourService.updateTour(tour);
        log.info("In updateTour {} successfully updated", tour);
        return new ResponseEntity<>(tour, OK);
    }
}
