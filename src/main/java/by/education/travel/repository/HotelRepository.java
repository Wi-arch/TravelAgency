package by.education.travel.repository;

import by.education.travel.entity.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Integer> {
}
