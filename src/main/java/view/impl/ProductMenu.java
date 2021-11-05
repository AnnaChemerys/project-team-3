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
import java.util.List;
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
            System.out.println(productToAdd);
            System.out.println("Enter amount: ");
            int productAmount = scanner.nextInt();
            orderService.addProductToOrder(productToAdd, productAmount);

        } else {
            System.out.println("Invalid ID");
        }
        show();
    }

    private void addProduct() {
        System.out.println("Enter product name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.println("Enter product price (delim: \",\"): ");

        float price = -1;
        try {
            price = scanner.nextFloat();
        } catch (InputMismatchException ignored) {
        }

        System.out.println("Enter product amount: ");

        int amount = -1;
        try {
            amount = scanner.nextInt();
        } catch (InputMismatchException ignored) {
        }

        System.out.println("Product category: ");
        for (int i = 0; i < ProductCategories.values().length; i++) {
            System.out.println((i + 1) + " " + ProductCategories.values()[i].toString());
        }

        System.out.println("Enter product category: ");
        scanner.nextLine();
        ProductCategories category = null;

        try {
            int i = scanner.nextInt();
            if (i <= ProductCategories.values().length && i > 0) {
                category = ProductCategories.values()[i - 1];
            }
        } catch (IllegalArgumentException e) {
        }
        Product product = new Product(price, name, amount, category);

        productService.saveProduct(product);

        show();
    }

    private void editProduct() {
        System.out.println("Enter product ID: ");
        scanner.nextLine();
        String productId = scanner.nextLine();

        Product productToAdd = productService.getProductById(productId);

        if (productToAdd != null) {

            System.out.println("Enter product name: ");
            scanner.nextLine();

            String name = scanner.nextLine();

            System.out.println("Enter product price (delim: \",\"): ");
            scanner.nextLine();

            float price = -1;
            try {
                price = scanner.nextFloat();
            } catch (InputMismatchException ignored) {
            }

            System.out.println("Enter product amount: ");
            scanner.nextLine();

            int amount = -1;
            try {
                amount = scanner.nextInt();
            } catch (InputMismatchException ignored) {
            }

            System.out.println("Product category: ");
            for (int i = 0; i < ProductCategories.values().length; i++) {
                System.out.println((i + 1) + " " + ProductCategories.values()[i].toString());
            }

            System.out.println("Enter product category: ");
            scanner.nextLine();
            ProductCategories category = null;

            try {
                int i = scanner.nextInt();
                if (i <= ProductCategories.values().length && i > 0) {
                    category = ProductCategories.values()[i - 1];
                }
            } catch (IllegalArgumentException e) {
            }

            productToAdd.setName(name);
            productToAdd.setAmount(amount);
            productToAdd.setCategory(category);
            productToAdd.setPrice(price);
            productService.updateProduct(productToAdd);
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
            int increase = 3;
            int page = 0;
            int[] pageIndex = pageIndex(productService.getAllProducts(),increase,page);
            boolean exitz = false;
            while (!exitz) {
                System.out.println("Product list:");
                productService.getAllProducts().subList(pageIndex[1], pageIndex[2]).forEach(x -> System.out.println("\t" + x));
                if (pageIndex[0] == 0) {
                    System.out.println("        / 2. Next / 3. Exit");
                } else if ((pageIndex[0] * increase) + increase >= productService.getAllProducts().size()) {
                    System.out.println("1. Prev /         / 3. Exit");
                } else {
                    System.out.println("1. Prev / 2. Next / 3. Exit");
                }

                int userChoise = -1;
                try {
                    userChoise = scanner.nextInt();
                } catch (InputMismatchException ignored) {
                }

                switch (userChoise) {
                    case 1 -> pageIndex = pageIndex(productService.getAllProducts(), increase, --pageIndex[0]);
                    case 2 -> pageIndex = pageIndex(productService.getAllProducts(), increase, ++pageIndex[0]);
                    case 3 -> exitz = true;
                }

            }
        }
        show();
    }

    public int[] pageIndex(List items, int increase, int page) {
        int pageOut;
        int start;
        int end;
        int[] index = new int[3];
        int maxPages = items.size() / increase;

        if (page < 0) {
            pageOut = 0;
            start = pageOut;
            end = increase;
        } else if (page >= maxPages) {
            pageOut = maxPages;
            start = pageOut*increase;
            end = items.size();
        } else {
            pageOut = page;
            start = page*increase;
            end = start+increase;
        }

        index[0] = pageOut;
        index[1] = start;
        index[2] = end;
        return index;
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
