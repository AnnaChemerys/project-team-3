package view.impl;

import model.Product;
import model.ProductCategories;
import service.ProductService;
import service.ProductServiceImpl;
import view.Menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SearchProductMenu implements Menu {

    private final ProductService productService = new ProductServiceImpl();
    private final String[] items = {"1. Search by name", "2. Search by category", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException ignored) {
                System.out.println("Incorrect input");
                scanner.nextLine();
                show();
            }
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
        } catch (Exception ignored) {
        }
        if (productService.searchProductsByCategory(categories).size() > 0) {
            productService.searchProductsByCategory(categories).stream().forEach(System.out::println);
        } else {
            System.out.println("There is no products with this category");
        }
        show();
    }

    private void searchByName() {
        System.out.println("Enter product name:");
        String productName = scanner.nextLine();
        List<Product> products = productService.searchProducts(productName);
        if (products.size() > 0) {
            products.stream().forEach(System.out::println);
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
