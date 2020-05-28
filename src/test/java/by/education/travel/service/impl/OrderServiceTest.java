package by.education.travel.service.impl;

import by.education.travel.entity.Order;
import by.education.travel.entity.Tour;
import by.education.travel.entity.User;
import by.education.travel.repository.OrderRepository;
import by.education.travel.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        Order orderToSave = buildValidOrder(0);
        Order savedOrder = buildValidOrder(1);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        orderToSave = orderService.saveOrder(orderToSave);
        assertEquals(savedOrder, orderToSave);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testGetOrderById() {
        Order orderToBeReturned = buildValidOrder(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderToBeReturned));
        Order actualOrder = orderService.getOrderById(1);
        assertEquals(orderToBeReturned, actualOrder);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllCountries() {
        List<Order> expected = new ArrayList<>();
        expected.add(buildValidOrder(1));
        expected.add(buildValidOrder(2));
        expected.add(buildValidOrder(3));
        List<Order> actual = null;
        when(orderRepository.findAll()).thenReturn(expected);
        actual = orderService.getAllOrders();
        assertEquals(expected, actual);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteOrder() {
        Order needToDelete = buildValidOrder(1);
        doNothing().when(orderRepository).delete(needToDelete);
        orderService.deleteOrder(needToDelete);
        verify(orderRepository, times(1)).delete(needToDelete);
    }

    @Test
    public void testGetOrderByNonExistingId() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        Order nonExistingOrder = orderService.getOrderById(1);
        assertNull(nonExistingOrder);
        verify(orderRepository, times(1)).findById(1);
    }

    private Order buildValidOrder(int id) {
        Order order = new Order();
        order.setTour(new Tour());
        order.setUser(new User());
        order.setId(id);
        return order;
    }
}
