package by.education.travel.service;

import by.education.travel.entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);

    Hotel getHotelById(int id);

    List<Hotel> getAllHotels();

    void deleteHotel(Hotel hotel);

    void updateHotel(Hotel hotel);
}
