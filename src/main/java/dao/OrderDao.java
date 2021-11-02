package dao;

import model.Order;
import model.User;

import java.util.List;

public class OrderDao extends AbstractDao<Order> {

    @Override
    protected String getFileName() {
        return FileNames.ORDERS.getFileName();
    }

    @Override
    public void update(Order order) {
        List<Order> tempList = items;
        for (Order orderTemp : tempList) {
            if (orderTemp.getUser() != null && orderTemp.getUser().equals(order.getUser())) {
                orderTemp.setProducts(order.getProducts());
                orderTemp.setApproved(order.isApproved());
            }
        }
        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }

    public Order getOrderByUser(User user) {
        for (Order orderTemp : items) {
            if (orderTemp.getUser() != null && orderTemp.getUser().equals(user) && orderTemp.isApproved() == false) {
                return orderTemp;
            }
        }
        return null;
    }
}
