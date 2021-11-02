package service;

import model.Order;
import model.Product;

import java.util.List;

public interface OrderService {

    void addProductToOrder(Product product);

    void approve(Order order);

    List<Order> getOrders();

    Order getOrderByUser();

    List<Order> getAllNotApprovedOrders();
}
