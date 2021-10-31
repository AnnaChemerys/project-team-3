package view.impl;

import dao.ProductDao;
import dao.ProductDaoImpl;
import model.User;
import util.CurrentUser;
import view.Menu;

import java.util.Scanner;

public class ProductMenu implements Menu {
    private final ProductDao productDao = new ProductDaoImpl();
    private final User.UserRole currentRole = CurrentUser.user.getRole();
    private final String[] items = currentRole == User.UserRole.USER
            ? new String[]{"1. Product list", "2. Search product", "3. Order checkout" , "0. Exit"}
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
                        case 3 -> orderCheckout();
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

    private void addProduct() {
        System.out.println("Temporarily unavailable");
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
        if(productDao.getAll().size() <= 0) {
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
