package service;

import model.Order;
import model.Product;

public interface OrderService {

    void addProductToOrder(Product product) throws Exception;

    void approve(Order order);
}
