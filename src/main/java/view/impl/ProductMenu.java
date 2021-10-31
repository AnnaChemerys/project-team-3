package view.impl;

import dao.ProductDao;
import dao.ProductDaoImpl;
import model.Product;
import model.ProductCategories;
import model.User;
import util.CurrentUser;
import view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductMenu implements Menu {
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
                        case 2 -> searchProduct();
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
        System.out.println("Temporarily unavailable");
        show();
    }

    private void addProduct() {
        boolean exists = false;
        ProductDao productDao = new ProductDaoImpl();
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
        ProductCategories category = ProductCategories.parse(scanner.nextLine().toUpperCase());
        Product product = new Product(price, name, amount, category);
        for (Product p : productDao.getAll()) {
            if (Float.compare(p.getPrice(), product.getPrice()) == 0 && p.getName().equals(product.getName()) && p.getCategory() == product.getCategory()) {
                p.setAmount(p.getAmount() + product.getAmount());
                productDao.update(p);
                exists = true;
                break;
            }
        }
        boolean isValid = price != -1 && amount != -1 && category != null;

        if (isValid && !exists) {
            productDao.save(product);
        }

        if(!isValid){
            System.out.println("Incorrect input!");
        }
        show();
    }

    private void editProduct() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void orderCheckout() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void searchProduct() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void productList() {
        ProductDao productDao = new ProductDaoImpl();
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
        new UserMainMenu().show();
    }
}
