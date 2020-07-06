package by.education.travel.service.impl;

import by.education.travel.entity.User;
import by.education.travel.exception.InvalidSpecificationException;
import by.education.travel.repository.UserRepository;
import by.education.travel.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private static final Random RANDOM = new Random();

    @Test
    public void testSaveUser() {
        User userToSave = buildValidUser(0);
        User savedUser = buildValidUser(1);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        userToSave = userService.saveUser(userToSave);
        assertEquals(savedUser, userToSave);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        User userToBeReturned = buildValidUser(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(userToBeReturned));
        User actualUser = userService.getUserById(1);
        assertEquals(userToBeReturned, actualUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllCountries() {
        List<User> expected = new ArrayList<>();
        expected.add(buildValidUser(1));
        expected.add(buildValidUser(2));
        expected.add(buildValidUser(3));
        List<User> actual = null;
        when(userRepository.findAll()).thenReturn(expected);
        actual = userService.getAllUsers();
        assertEquals(expected, actual);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUser() {
        User needToDelete = buildValidUser(1);
        doNothing().when(userRepository).delete(needToDelete);
        userService.deleteUser(needToDelete);
        verify(userRepository, times(1)).delete(needToDelete);
    }

    @Test
    public void testGetUserByNonExistingId() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        User nonExistingUser = userService.getUserById(1);
        assertNull(nonExistingUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testFindBySpecificationPositive() {
        String specification = "id:1";
        List<User> userList = new ArrayList<>();
        userList.add(buildValidUser(1));
        when(userRepository.findAll(any())).thenReturn(userList);
        List<User> actual = userService.findBySpecification(specification);
        verify(userRepository, times(1)).findAll(any());
        assertEquals(userList, actual);
    }

    @Test(expected = InvalidSpecificationException.class)
    public void testFindBySpecificationNegativeInvalidSpecification() {
        String specification = "id:1,age";
        userService.findBySpecification(specification);
        verify(userRepository, times(0)).findAll(any());
    }

    private User buildValidUser(int id) {
        User user = new User();
        user.setName("Test name");
        user.setSurname("Test surname");
        user.setAge(RANDOM.nextInt(50) + 18);
        user.setId(id);
        return user;
    }
}
