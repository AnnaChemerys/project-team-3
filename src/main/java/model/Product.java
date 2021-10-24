package model;

public class Product {
    private int id; // Идентификатор продукта
    private int count; // Кол-во продуктов на складе
    private String name; // Наименование
    private Prod_category category; // Категория
    private Float price; // Цена
    private final String currency = "UAH"; // Валюта

    public Product(int id, int count, String name, Prod_category category, Float price) {
        this.id = id;
        this.count = count;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

enum Prod_category {
     WATCHES,
     SMARTPHONES,
     COMPUTERS
}


