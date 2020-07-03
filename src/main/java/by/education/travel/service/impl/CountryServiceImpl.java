package by.education.travel.service.impl;

import by.education.travel.entity.Country;
import by.education.travel.repository.CountryRepository;
import by.education.travel.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country getCountryById(int id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Country> getAllCountries() {
        return (List) countryRepository.findAll();
    }

    @Override
    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public void updateCountry(Country country) {
        countryRepository.save(country);
    }
}
