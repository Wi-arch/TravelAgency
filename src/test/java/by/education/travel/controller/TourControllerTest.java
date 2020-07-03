package by.education.travel.controller;

import by.education.travel.entity.Tour;
import by.education.travel.service.TourService;
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

import java.math.BigDecimal;
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
public class TourControllerTest {

    @MockBean
    private TourService tourService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/tour/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testGetTourById() throws Exception {
        Tour validTour = buildValidTour(1);
        String expectedContent = convertToJsonString(validTour);
        when(tourService.getTourById(1)).thenReturn(validTour);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(tourService, times(1)).getTourById(1);
    }

    @Test
    public void testGetNonExistingTourById() throws Exception {
        when(tourService.getTourById(anyInt())).thenReturn(null);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllTours() throws Exception {
        List<Tour> tours = new ArrayList<>();
        tours.add(buildValidTour(1));
        tours.add(buildValidTour(2));
        tours.add(buildValidTour(3));
        String expectedContent = convertToJsonString(tours);
        when(tourService.getAllTours()).thenReturn(tours);
        mockMvc.perform(get(URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(tourService, times(1)).getAllTours();
    }

    @Test
    public void testUpdateNonExistingTour() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveTour() throws Exception {
        Tour tourNeedToSave = buildValidTour(0);
        Tour savedTour = buildValidTour(1);
        String expectedContent = convertToJsonString(savedTour);
        when(tourService.saveTour(tourNeedToSave)).thenReturn(savedTour);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(convertToJsonString(tourNeedToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(tourService, times(1)).saveTour(tourNeedToSave);
    }

    @Test
    public void testDeleteTour() throws Exception {
        Tour tourNeedToDelete = buildValidTour(1);
        String expectedContent = convertToJsonString(tourNeedToDelete);
        when(tourService.getTourById(1)).thenReturn(tourNeedToDelete);
        doNothing().when(tourService).deleteTour(tourNeedToDelete);
        mockMvc.perform(delete(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(tourService, times(1)).deleteTour(tourNeedToDelete);
    }

    @Test
    public void testDeleteNonExistingTour() throws Exception {
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

    private Tour buildValidTour(int id) {
        Tour tour = new Tour();
        tour.setId(id);
        tour.setName("Test name");
        tour.setPrice(new BigDecimal("100"));
        return tour;
    }
}
