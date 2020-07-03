package by.education.travel.service.impl;

import by.education.travel.entity.Hotel;
import by.education.travel.repository.HotelRepository;
import by.education.travel.service.HotelService;
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
public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @MockBean
    private HotelRepository hotelRepository;

    @Test
    public void testSaveHotel() {
        Hotel HotelToSave = buildValidHotel(0);
        Hotel savedHotel = buildValidHotel(1);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(savedHotel);
        HotelToSave = hotelService.saveHotel(HotelToSave);
        assertEquals(savedHotel, HotelToSave);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    public void testGetHotelById() {
        Hotel HotelToBeReturned = buildValidHotel(1);
        when(hotelRepository.findById(1)).thenReturn(Optional.of(HotelToBeReturned));
        Hotel actualHotel = hotelService.getHotelById(1);
        assertEquals(HotelToBeReturned, actualHotel);
        verify(hotelRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllCountries() {
        List<Hotel> expected = new ArrayList<>();
        expected.add(buildValidHotel(1));
        expected.add(buildValidHotel(2));
        expected.add(buildValidHotel(3));
        List<Hotel> actual = null;
        when(hotelRepository.findAll()).thenReturn(expected);
        actual = hotelService.getAllHotels();
        assertEquals(expected, actual);
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteHotel() {
        Hotel needToDelete = buildValidHotel(1);
        doNothing().when(hotelRepository).delete(needToDelete);
        hotelService.deleteHotel(needToDelete);
        verify(hotelRepository, times(1)).delete(needToDelete);
    }

    @Test
    public void testGetHotelByNonExistingId() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Hotel nonExistingHotel = hotelService.getHotelById(1);
        assertNull(nonExistingHotel);
        verify(hotelRepository, times(1)).findById(1);
    }

    private Hotel buildValidHotel(int id) {
        Hotel hotel = new Hotel();
        hotel.setName("Test name");
        hotel.setId(id);
        return hotel;
    }
}
