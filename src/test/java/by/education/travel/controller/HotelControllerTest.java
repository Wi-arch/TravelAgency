package by.education.travel.controller;

import by.education.travel.entity.Hotel;
import by.education.travel.service.HotelService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {

    @MockBean
    private HotelService hotelService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/hotel/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testGetHotelById() throws Exception {
        Hotel validHotel = buildValidHotel(1);
        String expectedContent = convertToJsonString(validHotel);
        when(hotelService.getHotelById(1)).thenReturn(validHotel);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(hotelService, times(1)).getHotelById(1);
    }

    @Test
    public void testGetNonExistingHotelById() throws Exception {
        when(hotelService.getHotelById(anyInt())).thenReturn(null);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllHotels() throws Exception {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(buildValidHotel(1));
        hotels.add(buildValidHotel(2));
        hotels.add(buildValidHotel(3));
        String expectedContent = convertToJsonString(hotels);
        when(hotelService.getAllHotels()).thenReturn(hotels);
        mockMvc.perform(get(URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(hotelService, times(1)).getAllHotels();
    }

    @Test
    public void testSaveHotel() throws Exception {
        Hotel hotelNeedToSave = buildValidHotel(0);
        Hotel savedHotel = buildValidHotel(1);
        String expectedContent = convertToJsonString(savedHotel);
        when(hotelService.saveHotel(hotelNeedToSave)).thenReturn(savedHotel);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(convertToJsonString(hotelNeedToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(hotelService, times(1)).saveHotel(hotelNeedToSave);
    }

    @Test
    public void testDeleteHotel() throws Exception {
        Hotel hotelNeedToDelete = buildValidHotel(1);
        String expectedContent = convertToJsonString(hotelNeedToDelete);
        when(hotelService.getHotelById(1)).thenReturn(hotelNeedToDelete);
        doNothing().when(hotelService).deleteHotel(hotelNeedToDelete);
        mockMvc.perform(delete(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(hotelService, times(1)).deleteHotel(hotelNeedToDelete);
    }

    @Test
    public void testDeleteNonExistingHotel() throws Exception {
        mockMvc.perform(delete(URL_TEMPLATE + "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String convertToJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Hotel buildValidHotel(int id) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName("Test name");
        return hotel;
    }
}
