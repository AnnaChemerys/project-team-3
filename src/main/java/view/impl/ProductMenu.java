package view.impl;

import model.Order;
import model.Product;
import model.ProductCategories;
import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import util.CurrentUser;
import view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final User.UserRole currentRole = CurrentUser.user.getRole();

    private final String[] items = currentRole == User.UserRole.USER
            ? new String[]{"1. Product list", "2. Search product", "3. Add product to order", "4. Order checkout", "0. Exit"}
            : new String[]{"1. Product list", "2. Edit product", "3. Add product", "0. Exit"};

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
            switch (currentRole) {
                case USER -> {
                    switch (choice) {
                        case 1 -> productList();
                        case 2 -> new SearchProductMenu().show();
                        case 3 -> addProductToOrder();
                        case 4 -> orderCheckout();
                        case 0 -> exit();
                    }
                }
                case ADMIN -> {
                    switch (choice) {
                        case 1 -> productList();
                        case 2 -> editProduct();
                        case 3 -> addProduct();
                        case 0 -> exit();
                    }
                }
            }
        }
    }

    private void addProductToOrder() {
        System.out.println("Enter order ID: ");
        scanner.nextLine();
        String id = scanner.nextLine();

        Product productToAdd = productService.getProductById(id);

        if (productToAdd != null) {
            //noinspection OptionalGetWithoutIsPresent
            System.out.println(productToAdd);
            System.out.println("Enter amount: ");
            int productAmount = scanner.nextInt();
            orderService.addProductToOrder(productToAdd, productAmount);
            System.out.println("Product was successfully added");

        } else {
            System.out.println("Invalid ID");
        }
        show();
    }

    private void addProduct() {
        boolean exists = false;
        System.out.print("Enter product name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter product price (delim: \",\"): ");
        scanner.nextLine();

        float price = -1;
        try {
            price = scanner.nextFloat();
        } catch (InputMismatchException ignored) {
        }

        System.out.print("Enter product amount: ");
        scanner.nextLine();

        int amount = -1;
        try {
            amount = scanner.nextInt();
        } catch (InputMismatchException ignored) {
        }

        System.out.print("Enter product category: ");
        scanner.nextLine();
        ProductCategories category;
        try {
            category = ProductCategories.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            category = null;
        }
        Product product = new Product(price, name, amount, category);

        productService.saveProduct(product);
        System.out.println("Product was added successfully");

        show();
    }

    private void editProduct() {
        boolean exists = false;
        System.out.print("Enter product ID: ");
        scanner.nextLine();
        String productId = scanner.nextLine();

        Product productToAdd = productService.getProductById(productId);

        if (productToAdd != null) {

            System.out.print("Enter product name: ");
            scanner.nextLine();

            String name = scanner.nextLine();

            System.out.print("Enter product price (delim: \",\"): ");
            scanner.nextLine();

            float price = -1;
            try {
                price = scanner.nextFloat();
            } catch (InputMismatchException ignored) {
            }

            System.out.print("Enter product amount: ");
            scanner.nextLine();

            int amount = -1;
            try {
                amount = scanner.nextInt();
            } catch (InputMismatchException ignored) {
            }

            System.out.print("Enter product category: ");
            scanner.nextLine();
            ProductCategories category;
            try {
                category = ProductCategories.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                category = null;
            }

            productToAdd.setName(name);
            productToAdd.setAmount(amount);
            productToAdd.setCategory(category);
            productToAdd.setPrice(price);
            productService.updateProduct(productToAdd);
            System.out.println("Product was changed successfully");
        } else {
            System.out.println("No product with such ID");
        }
        show();
    }

    private void orderCheckout() {
        Order order = orderService.getOrderByUser();
        System.out.println(order != null ? order : "----No orders----");
        show();
    }

    private void productList() {
        if (productService.getAllProducts().size() <= 0) {
            System.out.println("----No products----");
        } else {
            System.out.println("Product list:");
            productService.getAllProducts().forEach(x -> System.out.println("\t" + x));
        }
        show();
    }

    @Override
    public void exit() {
        if (CurrentUser.user.getRole() == User.UserRole.USER) {
            new UserMainMenu().show();
        } else {
            new AdminMainMenu().show();
        }
    }
}
