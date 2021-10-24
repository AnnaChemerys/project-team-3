package model;

public class Product {
    private int id; // Идентификатор продукта
    private int count; // Кол-во продуктов на складе
    private String name; // Наименование
    private Prod_category category; // Категория
    private Float price; // Цена
    private final String currency = "UAH"; // Валюта
}

enum Prod_category {
     WATCHES,
     SMARTPHONES,
     COMPUTERS
}


