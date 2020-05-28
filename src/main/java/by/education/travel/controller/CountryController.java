package by.education.travel.controller;

import by.education.travel.entity.Country;
import by.education.travel.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/country/")
@Slf4j
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int id) {
        Country country = countryService.getCountryById(id);
        log.info("In getCountryById {} country: {}", id, country);
        if (country == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(country, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        log.info("In getAllCountries find {}", countries);
        if (countries == null || countries.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(countries, OK);
    }

    @PostMapping("")
    public ResponseEntity<Country> saveCountry(@RequestBody Country country) {
        log.info("In saveCountry {}", country);
        if (country == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        country = countryService.saveCountry(country);
        return new ResponseEntity<>(country, CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") int id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        countryService.deleteCountry(country);
        log.info("In deleteCountry {} successfully deleted", country);
        return new ResponseEntity<>(country, NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        if (country == null || countryService.getCountryById(country.getId()) == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        countryService.updateCountry(country);
        log.info("In updateCountry {} successfully updated", country);
        return new ResponseEntity<>(country, OK);
    }
}
