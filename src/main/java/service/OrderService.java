package service;

import model.Order;
import model.Product;

import java.util.List;

public interface OrderService {

    void addProductToOrder(Product product, int amount);

    void approve(Order order);

    void refuse(Order order);

    List<Order> getOrders();

    Order getOrderByUser();

    List<Order> getAllNotApprovedOrders();

    Order getOrderById(String id);
}
