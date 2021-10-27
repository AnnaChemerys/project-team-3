package dao;

import model.Order;
import model.User;

import java.util.List;

public interface OrderDao {

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order getByLogin(User user);

    List<Order> getAll();

}