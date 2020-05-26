package by.education.travel.controller;

import by.education.travel.entity.Hotel;
import by.education.travel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/hotel/")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") int id) {
        Hotel hotel = hotelService.getHotelById(id);
        log.info("In getHotelById {} country: {}", id, hotel);
        if (hotel == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(hotel, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        log.info("In getAllHotels find {}", hotels);
        if (hotels == null || hotels.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, OK);
    }

    @PostMapping("")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        log.info("In saveHotel {}", hotel);
        if (hotel == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        hotelService.saveHotel(hotel);
        return new ResponseEntity<>(hotel, CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable("id") int id) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        hotelService.deleteHotel(hotel);
        log.info("In deleteHotel {} successfully deleted", hotel);
        return new ResponseEntity<>(hotel, NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
        if (hotel == null || hotelService.getHotelById(hotel.getId()) == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        hotelService.updateHotel(hotel);
        log.info("In updateHotel {} successfully updated", hotel);
        return new ResponseEntity<>(hotel, OK);
    }
}
