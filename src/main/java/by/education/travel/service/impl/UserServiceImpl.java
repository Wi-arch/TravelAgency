package by.education.travel.service.impl;

import by.education.travel.entity.User;
import by.education.travel.repository.UserRepository;
import by.education.travel.repository.specification.SpecificationsBuilder;
import by.education.travel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Pattern PATTERN = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return (List) userRepository.findAll();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findBySpecification(String specification) {
        SpecificationsBuilder<User> builder = new SpecificationsBuilder<>();
        Matcher matcher = PATTERN.matcher(specification + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return userRepository.findAll(builder.build());
    }
}
