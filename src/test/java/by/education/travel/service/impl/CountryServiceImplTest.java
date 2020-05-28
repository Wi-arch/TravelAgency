package by.education.travel.service.impl;

import by.education.travel.entity.Country;
import by.education.travel.repository.CountryRepository;
import by.education.travel.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceImplTest {

    @Autowired
    private CountryService countryService;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    public void testSaveCountry() {
        Country countryToSave = buildValidCountry(0);
        Country savedCountry = buildValidCountry(1);
        when(countryRepository.save(any(Country.class))).thenReturn(savedCountry);
        countryToSave = countryService.saveCountry(countryToSave);
        assertEquals(savedCountry, countryToSave);
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    public void testGetCountryById() {
        Country countryToBeReturned = buildValidCountry(1);
        when(countryRepository.findById(1)).thenReturn(Optional.of(countryToBeReturned));
        Country actualCountry = countryService.getCountryById(1);
        assertEquals(countryToBeReturned, actualCountry);
        verify(countryRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllCountries() {
        List<Country> expected = new ArrayList<>();
        expected.add(buildValidCountry(1));
        expected.add(buildValidCountry(2));
        expected.add(buildValidCountry(3));
        List<Country> actual = null;
        when(countryRepository.findAll()).thenReturn(expected);
        actual = countryService.getAllCountries();
        assertEquals(expected, actual);
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCountry() {
        Country needToDelete = buildValidCountry(1);
        doNothing().when(countryRepository).delete(needToDelete);
        countryService.deleteCountry(needToDelete);
        verify(countryRepository, times(1)).delete(needToDelete);
    }

    @Test
    public void testGetCountryByNonExistingId() {
        when(countryRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Country nonExistingCountry = countryService.getCountryById(1);
        assertNull(nonExistingCountry);
        verify(countryRepository, times(1)).findById(1);
    }

    private Country buildValidCountry(int id) {
        Country country = new Country();
        country.setId(id);
        country.setName("Test name");
        return country;
    }
}