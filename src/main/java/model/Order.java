package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order implements Serializable, HasId {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private User user;
    private List<Product> products;
    private boolean approved;

    public Order() {
        this.id = UUID.randomUUID().toString();
    }

    public Order(User user, List<Product> products, boolean approved) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.products = products;
        this.approved = approved;
    }

    public Order(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.products = new ArrayList<>();
        this.approved = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return approved == order.approved && Objects.equals(user, order.user) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, products, approved);
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
