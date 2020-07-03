package by.education.travel.controller;

import by.education.travel.entity.User;
import by.education.travel.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/user/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testGetUserById() throws Exception {
        User validUser = buildValidUser(1);
        String expectedContent = convertToJsonString(validUser);
        when(userService.getUserById(1)).thenReturn(validUser);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testGetNonExistingUserById() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(null);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(buildValidUser(1));
        users.add(buildValidUser(2));
        users.add(buildValidUser(3));
        String expectedContent = convertToJsonString(users);
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get(URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testSaveUser() throws Exception {
        User userNeedToSave = buildValidUser(0);
        User savedUser = buildValidUser(1);
        String expectedContent = convertToJsonString(savedUser);
        when(userService.saveUser(userNeedToSave)).thenReturn(savedUser);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(convertToJsonString(userNeedToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(userService, times(1)).saveUser(userNeedToSave);
    }

    @Test
    public void testDeleteUser() throws Exception {
        User userNeedToDelete = buildValidUser(1);
        String expectedContent = convertToJsonString(userNeedToDelete);
        when(userService.getUserById(1)).thenReturn(userNeedToDelete);
        doNothing().when(userService).deleteUser(userNeedToDelete);
        mockMvc.perform(delete(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(userService, times(1)).deleteUser(userNeedToDelete);
    }

    @Test
    public void testDeleteNonExistingUser() throws Exception {
        mockMvc.perform(delete(URL_TEMPLATE + "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String convertToJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildValidUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName("Test name");
        user.setSurname("Test surname");
        return user;
    }
}
