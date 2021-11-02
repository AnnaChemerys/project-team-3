package view.impl;

import service.OrderService;
import service.OrderServiceImpl;
import view.Menu;

import java.util.Scanner;

public class UserMainMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final String[] items = {"1. Product menu", "2. My orders", "(3. Message admin)", "0. Exit"};

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> new ProductMenu().show();
                case 2 -> myOrders();
                case 3 -> messageAdmin();
                case 0 -> exit();
            }

        }
    }

    private void messageAdmin() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void myOrders() {
        System.out.println(orderService.getOrderByUser() == null ? "-----No orders-----" : orderService.getOrderByUser());
        show();
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}
