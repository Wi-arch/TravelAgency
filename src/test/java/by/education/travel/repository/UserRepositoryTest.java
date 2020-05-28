package by.education.travel.repository;

import by.education.travel.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource("/application-test.properties")
@Sql(value = "classpath:createUserBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:createUserAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindUserById() {
        User expectedUser = buildValidUser(2, "AAA", "AAA");
        User actualUser = userRepository.findById(2).orElse(null);
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(buildValidUser(2, "AAA", "AAA"));
        expectedUsers.add(buildValidUser(3, "BBB", "BBB"));
        expectedUsers.add(buildValidUser(4, "CCC", "CCC"));
        expectedUsers.add(buildValidUser(5, "DDD", "DDD"));
        List<User> actualUsers = (List) userRepository.findAll();
        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testSaveUser() {
        User userToBeSaved = buildValidUser(1, "EEE", "EEE");
        userRepository.save(userToBeSaved);
        User actualUser = userRepository.findById(1).orElse(null);
        assertNotNull(actualUser);
        assertEquals(userToBeSaved, actualUser);
    }

    @Test
    public void testUpdateUser() {
        User userToBeUpdated = buildValidUser(2, "new_AAA", "new_AAA");
        userRepository.save(userToBeUpdated);
        User actualUser = userRepository.findById(2).orElse(null);
        assertNotNull(actualUser);
        assertEquals(userToBeUpdated, actualUser);
    }

    @Test
    public void testDeleteUser() {
        User userToBeDeleted = buildValidUser(2, "AAA", "AAA");
        userRepository.delete(userToBeDeleted);
        User nonExistingUser = userRepository.findById(2).orElse(null);
        assertNull(nonExistingUser);
    }

    private User buildValidUser(int id, String name, String surname) {
        User user = new User();
        user.setId(id);
        user.setName("test_user_name_" + name);
        user.setSurname("test_user_surname_" + surname);
        return user;
    }
}
