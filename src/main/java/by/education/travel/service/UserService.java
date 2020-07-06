package by.education.travel.service;

import by.education.travel.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(int id);

    List<User> getAllUsers();

    void deleteUser(User user);

    void updateUser(User user);

    List<User> findBySpecification(String specification);
}
