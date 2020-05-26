package by.education.travel.service.impl;

import by.education.travel.entity.Hotel;
import by.education.travel.repository.HotelRepository;
import by.education.travel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(int id) {
        return hotelRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return (List) hotelRepository.findAll();
    }

    @Override
    public void deleteHotel(Hotel hotel) {
        hotelRepository.delete(hotel);
    }

    @Override
    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }
}
