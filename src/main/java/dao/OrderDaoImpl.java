package dao;

import model.Order;
import model.User;
import util.FileOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDaoImpl implements OrderDao {

    private final String filename = "orders.ser";
    private List<Order> orders;
    private final FileOperation fileOperation = new FileOperation();


    public OrderDaoImpl() {
        List<Object> raw = fileOperation.readFromFile(filename);
        orders = raw.stream()
                .map(o -> (Order) o)
                .collect(Collectors.toList());
    }

    private void writeToFile(List<Order> orders) {
        List<Object> objects = orders.stream()
                .map(o -> (Object) o)
                .collect(Collectors.toList());

        fileOperation.writeIntoFile(objects, filename);
    }

    @Override
    public void save(Order order) {
        orders.add(order);
        writeToFile(orders);
    }

    @Override
    public void update(Order order) {
        for (Order orderTemp : orders) {
            if (orderTemp.getUser().equals(order.getUser())) {
                orderTemp.setProducts(order.getProducts());
                orderTemp.setApproved(order.isApproved());
                writeToFile(orders);
            }
        }
    }

    @Override
    public void delete(Order order) {
        int deleteItem = -1;

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).equals(order)) {
                deleteItem = i;
            }
        }

        if (deleteItem != -1) {
            orders.remove(deleteItem);
            writeToFile(orders);
        }
    }

    @Override
    public Order getByLogin(User user) {
        List<Order> newOrders = new ArrayList<>();
        for (Order orderTemp : orders) {
            if (orderTemp.getUser().equals(user) && orderTemp.isApproved() == false) {
                return orderTemp;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }
}
