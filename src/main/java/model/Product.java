package model;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private float price;
    private String name;
    private int amount;
    private ProductCategories category;

    public Product(int id, float price, String name, int amount, ProductCategories category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Product(float price, String name, int amount, ProductCategories category) {
        this.id = (int) (Math.random() * 1000);
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
