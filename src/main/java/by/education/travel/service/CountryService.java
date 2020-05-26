package by.education.travel.service;

import by.education.travel.entity.Country;

import java.util.List;

public interface CountryService {

    Country saveCountry(Country country);

    Country getCountryById(int id);

    List<Country> getAllCountries();

    void deleteCountry(Country country);

    void updateCountry(Country country);
}
