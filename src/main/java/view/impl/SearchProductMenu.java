package view.impl;

import dao.AbstractDao;
import dao.ProductDao;
import model.Product;
import model.ProductCategories;
import view.Menu;

import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchProductMenu implements Menu {
    private final AbstractDao<Product> productDao = new ProductDao();
    private final String[] items = {"1. Search by name", "2. Search by category", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> searchByName();
                case 2 -> searchByCategory();
                case 0 -> exit();
            }
        }
    }

    private void searchByCategory() {
        System.out.println("Enter product category:");
        ProductCategories categories = null;
        try {
            categories = ProductCategories.valueOf(scanner.nextLine().toUpperCase());
        } catch (Exception ignored){
        }
        if (productDao.getAll().stream().map(Product::getCategory).collect(Collectors.toList()).contains(categories)){
            ProductCategories finalCategories = categories;
            productDao.getAll().stream().filter(x -> x.getCategory().equals(finalCategories)).forEach(System.out::println);
        } else {
            System.out.println("There is no products with this category");
        }
        show();
    }

    private void searchByName() {
        System.out.println("Enter product name:");
        String productName = scanner.nextLine();
        if (productDao.getAll().stream().map(Product::getName).collect(Collectors.toList()).contains(productName)){
            //noinspection OptionalGetWithoutIsPresent
            System.out.println(productDao.getAll().stream().filter(x -> x.getName().equals(productName)).findFirst().get());
        } else {
            System.out.println("There is no product with this name");
        }
        show();
    }

    @Override
    public void exit() {
        new ProductMenu().show();
    }
}
