package by.education.travel.service;

import by.education.travel.entity.Tour;

import java.util.List;

public interface TourService {

    Tour saveTour(Tour tour);

    Tour getTourById(int id);

    List<Tour> getAllTours();

    void deleteTour(Tour tour);

    void updateTour(Tour tour);
}
