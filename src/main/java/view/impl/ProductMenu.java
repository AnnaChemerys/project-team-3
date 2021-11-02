package view.impl;

import dao.AbstractDao;
import dao.ProductDao;
import model.Product;
import model.ProductCategories;
import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import util.CurrentUser;
import view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductMenu implements Menu {
    private final AbstractDao<Product> productDao = new ProductDao();
    private final OrderService orderService = new OrderServiceImpl();
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
            int choice = scanner.nextInt();
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
        if (productDao.getAll().stream().map(Product::getId).collect(Collectors.toList()).contains(id)) {
            //noinspection OptionalGetWithoutIsPresent
            Product product = productDao.getAll().stream().filter(x -> x.getId().equals(id)).findFirst().get();
            System.out.println(product);
            System.out.println("Enter amount: ");
            int productAmount = scanner.nextInt();
            orderService.addProductToOrder(product, productAmount);
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
        float price = -1;
        try {
            price = scanner.nextFloat();
        } catch (InputMismatchException ignored) {
        }

        System.out.print("Enter product amount: ");
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
        for (Product p : productDao.getAll()) {
            if (Float.compare(p.getPrice(), product.getPrice()) == 0 && p.getName().equals(product.getName()) && p.getCategory() == product.getCategory()) {
                exists = true;
                break;
            }
        }
        boolean isValid = price != -1 && amount != -1 && category != null;

        if (exists) {
            System.out.println("This product already exists");
        }

        if (isValid && !exists) {
            productDao.save(product);
        }

        if (!isValid) {
            System.out.println("Incorrect input!");
        }
        show();
    }

    private void editProduct() {
        boolean exists = false;
        System.out.print("Enter product ID: ");
        scanner.nextLine();
        String productId = scanner.nextLine();

        if (productDao.getAll().stream().map(Product::getId).collect(Collectors.toList()).contains(productId)) {
            Product product = productDao.getById(productId);
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            System.out.print("Enter product price (delim: \",\"): ");
            float price = -1;
            try {
                price = scanner.nextFloat();
            } catch (InputMismatchException ignored) {
            }
            scanner.nextLine();
            System.out.print("Enter product amount: ");
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
            boolean isValid = price != -1 && amount != -1 && category != null;
            if (!isValid) {
                System.out.println("Incorrect input!");
            } else {
                for (Product p : productDao.getAll()) {
                    if (p.equals(product)) continue;
                    if (Float.compare(p.getPrice(), price) == 0 && p.getName().equals(name) && p.getCategory() == category) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("You already have such product");
                } else {
                    product.setName(name);
                    product.setAmount(amount);
                    product.setCategory(category);
                    product.setPrice(price);
                    productDao.update(product);
                }
            }
        } else {
            System.out.println("No product with such ID");
        }
        show();
    }

    private void orderCheckout() {
        System.out.println("Temporarily unavailable");

        show();
    }

    private void productList() {
        if (productDao.getAll().size() <= 0) {
            System.out.println("----No products----");
        } else {
            System.out.println("Product list:");
            productDao.getAll().forEach(x -> System.out.println("\t" + x));
        }
        show();
    }

    @Override
    public void exit() {
        if(CurrentUser.user.getRole() == User.UserRole.USER){
            new UserMainMenu().show();
        } else {
            new AdminMainMenu().show();
        }
    }
}
