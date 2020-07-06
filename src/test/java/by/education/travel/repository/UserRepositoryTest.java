package by.education.travel.repository;

import by.education.travel.entity.User;
import by.education.travel.repository.specification.SearchCriteria;
import by.education.travel.repository.specification.UserSpecification;
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
        User expectedUser = buildValidUser(2, "AAA", "AAA", 25);
        User actualUser = userRepository.findById(2).orElse(null);
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(buildValidUser(2, "AAA", "AAA", 25));
        expectedUsers.add(buildValidUser(3, "BBB", "BBB", 31));
        expectedUsers.add(buildValidUser(4, "CCC", "CCC", 33));
        expectedUsers.add(buildValidUser(5, "DDD", "DDD", 29));
        List<User> actualUsers = (List) userRepository.findAll();
        System.out.println(actualUsers);
        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testSaveUser() {
        User userToBeSaved = buildValidUser(1, "EEE", "EEE", 27);
        userRepository.save(userToBeSaved);
        User actualUser = userRepository.findById(1).orElse(null);
        assertNotNull(actualUser);
        assertEquals(userToBeSaved, actualUser);
    }

    @Test
    public void testUpdateUser() {
        User userToBeUpdated = buildValidUser(2, "new_AAA", "new_AAA", 25);
        userRepository.save(userToBeUpdated);
        User actualUser = userRepository.findById(2).orElse(null);
        assertNotNull(actualUser);
        assertEquals(userToBeUpdated, actualUser);
    }

    @Test
    public void testDeleteUser() {
        User userToBeDeleted = buildValidUser(2, "AAA", "AAA", 25);
        userRepository.delete(userToBeDeleted);
        User nonExistingUser = userRepository.findById(2).orElse(null);
        assertNull(nonExistingUser);
    }

    @Test
    public void testFindBySpecificationPositiveById() {
        User actualUser = buildValidUser(2, "AAA", "AAA", 25);
        UserSpecification specification = new UserSpecification(new SearchCriteria("id", ":", "2"));
        User expectedUser = userRepository.findOne(specification).orElse(null);
        assertNotNull(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindBySpecificationPositiveByAge() {
        List<User> actualUsers = new ArrayList<>();
        actualUsers.add(buildValidUser(3, "BBB", "BBB", 31));
        actualUsers.add(buildValidUser(4, "CCC", "CCC", 33));
        UserSpecification specification = new UserSpecification(new SearchCriteria("age", ">", "30"));
        List<User> expectedUsers = userRepository.findAll(specification);
        assertNotNull(expectedUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    private User buildValidUser(int id, String name, String surname, int age) {
        return new User(id, "test_user_name_" + name, "test_user_surname_" + surname, age);
    }
}
