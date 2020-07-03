package by.education.travel.controller;

import by.education.travel.entity.Order;
import by.education.travel.entity.Tour;
import by.education.travel.entity.User;
import by.education.travel.service.OrderService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/order/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testGetOrderById() throws Exception {
        Order validOrder = buildValidOrder(1);
        String expectedContent = convertToJsonString(validOrder);
        when(orderService.getOrderById(1)).thenReturn(validOrder);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    public void testGetNonExistingOrderById() throws Exception {
        when(orderService.getOrderById(anyInt())).thenReturn(null);
        mockMvc.perform(get(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(buildValidOrder(1));
        orders.add(buildValidOrder(2));
        orders.add(buildValidOrder(3));
        String expectedContent = convertToJsonString(orders);
        when(orderService.getAllOrders()).thenReturn(orders);
        mockMvc.perform(get(URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void testUpdateNonExistingOrder() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveOrder() throws Exception {
        Order orderNeedToSave = buildValidOrder(0);
        Order savedOrder = buildValidOrder(1);
        String expectedContent = convertToJsonString(savedOrder);
        when(orderService.saveOrder(orderNeedToSave)).thenReturn(savedOrder);
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON)
                .content(convertToJsonString(orderNeedToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(orderService, times(1)).saveOrder(orderNeedToSave);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Order orderNeedToDelete = buildValidOrder(1);
        String expectedContent = convertToJsonString(orderNeedToDelete);
        when(orderService.getOrderById(1)).thenReturn(orderNeedToDelete);
        doNothing().when(orderService).deleteOrder(orderNeedToDelete);
        mockMvc.perform(delete(URL_TEMPLATE + "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
        verify(orderService, times(1)).deleteOrder(orderNeedToDelete);
    }

    @Test
    public void testDeleteNonExistingOrder() throws Exception {
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

    private Order buildValidOrder(int id) {
        Order order = new Order();
        order.setId(id);
        order.setUser(new User());
        order.setTour(new Tour());
        return order;
    }
}
