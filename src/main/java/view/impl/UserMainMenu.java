package view.impl;


import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import util.CurrentUser;
import view.Menu;

import java.util.Scanner;
import java.util.stream.Collectors;

public class UserMainMenu implements Menu {

    private final User.UserRole currentRole = CurrentUser.user.getRole();
    private final String[] items = currentRole == User.UserRole.USER
            ? new String[]{"1. Product menu", "2. My orders", "(3. Message admin)", "0. Exit"}
            : new String[]{"1. Users menu", "2. Orders menu", "3. Products menu", "0. Exit"};

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (currentRole) {
                case USER -> {
                    switch (choice) {
                        case 1 -> new ProductMenu().show();
                        case 2 -> myOrders();
                        case 3 -> messageAdmin();
                        case 0 -> exit();
                    }
                }
                case ADMIN -> {
                    switch (choice) {
                        case 1 -> new UsersMenu().show();
                        case 2 -> ordersMenu();
                        case 3 -> new ProductMenu().show();
                        case 0 -> exit();
                    }
                }
            }
        }
    }

    private void ordersMenu() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void messageAdmin() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void myOrders() {
        System.out.println("Temporarily unavailable");
        show();
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}
