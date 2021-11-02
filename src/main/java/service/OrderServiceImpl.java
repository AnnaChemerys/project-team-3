package service;

import dao.OrderDao;
import dao.ProductDao;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDao();
    private ProductDao productDao = new ProductDao();

    @Override
    public void addProductToOrder(Product product) {

        isValidProductForOrder(product);
        Order existingOrder = orderDao.getOrderByUser(CurrentUser.user);

        if (existingOrder == null) {
            List<Product> newProduct = new ArrayList<>();
            newProduct.add(product);
            Order newOrder = new Order(CurrentUser.user, newProduct, false);
            orderDao.save(newOrder);
        } else {
            existingOrder.addProduct(product);
            orderDao.update(existingOrder);
        }
    }

    private void isValidProductForOrder(Product product) {
        Product tempProduct = productDao.getById(product.getId());
        if (tempProduct.getAmount() == 0) {
            System.out.println("Product amount is not enough.");
        }
    }

    @Override
    public void approve(Order order) {
        if (!order.isApproved()) {
            order.setApproved(true);
            orderDao.update(order);
        } else {
            System.out.println("This order is already approved");
        }
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.getAll();
    }

    @Override
    public Order getOrderByUser() {
        return orderDao.getOrderByUser(CurrentUser.user);
    }

    @Override
    public List<Order> getAllNotApprovedOrders() {
        List<Order> notApprovedOrders = new ArrayList<>();
        for (Order order: orderDao.getAll()) {
            if (!order.isApproved()){
                notApprovedOrders.add(order);
             }
        }
        return notApprovedOrders;
    }
}
