package view.impl;

import dao.UserDao;
import dao.UserDaoImpl;
import view.Menu;

import java.util.Scanner;

public class OrdersMenu implements Menu {
    private final UserDao userDao = new UserDaoImpl();
    private final String[] items = new String[]{"1. Orders list", "2. Confirm/ unconfirm order", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showAllOrders();
                case 2 -> confirmUnconfirmOrders();
                case 0 -> exit();
            }
        }
    }

    private void showAllOrders() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void confirmUnconfirmOrders() {
        System.out.println("Temporarily unavailable");
        show();
    }

    @Override
    public void exit() {
        new UserMainMenu().show();
    }
}
