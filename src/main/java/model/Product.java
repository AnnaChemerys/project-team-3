package model;

abstract class Product {
    private int id; // Идентификатор продукта
    private int count = 0; // Кол-во продуктов на складе
    private String name; // Наименование
    private Float amount; // Цена
    private final String currency = "UAH"; // Валюта

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count += count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public Float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

class Computers extends Product {
    public Computers(int id, int count, String name, Float amount) {
        setId(id);
        setCount(count);
        setName(name);
        setAmount(amount);
    }
}

class Watches extends Product {
    public Watches(int id, int count, String name, Float amount) {
        setId(id);
        setCount(count);
        setName(name);
        setAmount(amount);
    }
}

class Phones extends Product {
    public Phones(int id, int count, String name, Float amount) {
        setId(id);
        setCount(count);
        setName(name);
        setAmount(amount);
    }
}
