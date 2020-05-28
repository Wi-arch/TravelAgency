package by.education.travel.controller;

import by.education.travel.entity.Country;
import by.education.travel.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/country/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testGetCountryById() throws Exception {
        String expectedContent = "{\"id\":1,\"name\":\"Test name\"}";
        Country validCountry = buildValidCountry(1);
        when(countryService.getCountryById(1)).thenReturn(validCountry);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(countryService, times(1)).getCountryById(1);
    }

    @Test
    public void testGetAllCounties() throws Exception {
        String expectedContent = "[{\"id\":1,\"name\":\"Test name\"},{\"id\":2,\"name\":\"Test name\"},{\"id\":3,\"name\":\"Test name\"}]";
        List<Country> countries = new ArrayList<>();
        countries.add(buildValidCountry(1));
        countries.add(buildValidCountry(2));
        countries.add(buildValidCountry(3));
        when(countryService.getAllCountries()).thenReturn(countries);
        mockMvc.perform(get(URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(countryService, times(1)).getAllCountries();
    }

    @Test
    public void testSaveCountry() throws Exception {
        String expectedContent = "{\"id\":1,\"name\":\"Test name\"}";
        Country countryNeedToSave = buildValidCountry(0);
        Country savedCountry = buildValidCountry(1);
        when(countryService.saveCountry(countryNeedToSave)).thenReturn(savedCountry);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(convertToJsonString(countryNeedToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(countryService, times(1)).saveCountry(countryNeedToSave);
    }

    @Test
    public void testDeleteCountry() throws Exception {
        String expectedContent = "{\"id\":1,\"name\":\"Test name\"}";
        Country countryNeedToDelete = buildValidCountry(1);
        when(countryService.getCountryById(1)).thenReturn(countryNeedToDelete);
        doNothing().when(countryService).deleteCountry(countryNeedToDelete);
        mockMvc.perform(delete(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(countryService, times(1)).deleteCountry(countryNeedToDelete);
    }

    @Test
    public void testDeleteNonExistingCountry() throws Exception {
        mockMvc.perform(delete(URL_TEMPLATE + "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String convertToJsonString(Country country) {
        try {
            return OBJECT_MAPPER.writeValueAsString(country);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Country buildValidCountry(int id) {
        Country country = new Country();
        country.setId(id);
        country.setName("Test name");
        return country;
    }
}
