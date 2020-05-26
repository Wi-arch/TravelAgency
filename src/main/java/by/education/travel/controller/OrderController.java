package by.education.travel.controller;

import by.education.travel.entity.Order;
import by.education.travel.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/order/")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        log.info("In getOrderById {} order: {}", id, order);
        if (order == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(order, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        log.info("In getAllOrders find {}", orders);
        if (orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(orders, OK);
    }

    @PostMapping("")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        log.info("In saveOrder {}", order);
        if (order == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        orderService.saveOrder(order);
        return new ResponseEntity<>(order, CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        orderService.deleteOrder(order);
        log.info("In deleteOrder {} successfully deleted", order);
        return new ResponseEntity<>(order, NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        if (order == null || orderService.getOrderById(order.getId()) == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        orderService.updateOrder(order);
        log.info("In updateOrder {} successfully updated", order);
        return new ResponseEntity<>(order, OK);
    }
}
