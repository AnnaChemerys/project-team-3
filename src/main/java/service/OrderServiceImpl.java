package service;

import dao.OrderDao;
import dao.ProductDao;
import model.Order;
import model.Product;
import util.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao = new OrderDao();
    private final ProductDao productDao = new ProductDao();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public void addProductToOrder(Product product, int amount) {
        if (!isValidProductForOrder(product, amount)) return;
        Order existingOrder = orderDao.getOrderByUser(CurrentUser.user);
        product.setAmount(product.getAmount() - amount);
        Product tempProduct = new Product(product.getId(), product.getPrice(), product.getName(), amount, product.getCategory());
        if (existingOrder == null) {
            List<Product> newProduct = new ArrayList<>();
            newProduct.add(tempProduct);
            Order newOrder = new Order(CurrentUser.user, newProduct, false);
            orderDao.save(newOrder);
        } else {
            existingOrder.addProduct(tempProduct);
            orderDao.update(existingOrder);
        }
        productDao.update(product);
    }

    private boolean isValidProductForOrder(Product product, int amount) {
        Product tempProduct = productDao.getById(product.getId());
        if (tempProduct.getAmount() < amount) {
            System.out.println("Product amount is not enough.");
            return false;
        }
        return true;
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
    public void refuse(Order order) {
        approve(order);
        List<Product> products = new ArrayList<>();
        for (Product initialProduct : productDao.getAll()) {
            for (Product refusedProduct : order.getProducts()) {
                if (initialProduct.getId().equals(refusedProduct.getId())) {
                    products.add(new Product(initialProduct.getId(), initialProduct.getPrice(), initialProduct.getName(), initialProduct.getAmount() + refusedProduct.getAmount(), initialProduct.getCategory()));
                }
            }
        }
        for (Product product : products) {
            productService.updateProduct(product);
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
        for (Order order : orderDao.getAll()) {
            if (!order.isApproved()) {
                notApprovedOrders.add(order);
            }
        }
        return notApprovedOrders;
    }

    public Order getOrderById(String id) {
        return orderDao.getById(id);
    }

}
