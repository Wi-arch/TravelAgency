package by.education.travel.service;

import by.education.travel.entity.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);

    Order getOrderById(int id);

    List<Order> getAllOrders();

    void deleteOrder(Order order);

    void updateOrder(Order order);
}
