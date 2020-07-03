package by.education.travel.service.impl;

import by.education.travel.entity.Tour;
import by.education.travel.repository.TourRepository;
import by.education.travel.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public Tour getTourById(int id) {
        return tourRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tour> getAllTours() {
        return (List) tourRepository.findAll();
    }

    @Override
    public void deleteTour(Tour tour) {
        tourRepository.delete(tour);
    }

    @Override
    public void updateTour(Tour tour) {
        tourRepository.save(tour);
    }
}
