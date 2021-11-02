package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable, HasId {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private float price;
    private String name;
    private int amount;
    private ProductCategories category;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public Product(float price, String name, int amount, ProductCategories category) {
        this.id = UUID.randomUUID().toString();
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Product(String id, float price, String name, int amount, ProductCategories category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public String getId() {
        return id;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductCategories getCategory() {
        return category;
    }

    public void setCategory(ProductCategories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                '}';
    }
}
