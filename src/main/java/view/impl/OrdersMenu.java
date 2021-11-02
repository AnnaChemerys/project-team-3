package view.impl;

import model.Order;
import service.OrderService;
import service.OrderServiceImpl;
import view.Menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrdersMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final String[] items = new String[]{"1. Orders list", "2. Confirm/ unconfirm order", "0. Exit"};
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
                case 1 -> showAllOrders();
                case 2 -> confirmUnconfirmOrders();
                case 0 -> exit();
            }
        }
    }

    private void showAllOrders() {
        List<Order> orderList = orderService.getAllNotApprovedOrders();
        if (orderList.size() <= 0) {
            System.out.println("-----No orders-----");
        } else {
            orderList.forEach(System.out::println);
        }
        show();
    }

    private void confirmUnconfirmOrders() {
        System.out.println("Enter order ID: ");
        String id = scanner.nextLine();
        Order order = orderService.getOrderById(id);

        if (order != null) {
            System.out.println(order);
            System.out.println("Press 1 to approve order\n" +
                    "Press 2 to refuse order");
            int approveOrder = scanner.nextInt();
            switch (approveOrder) {
                case 1 -> {
                    orderService.approve(order);
                    System.out.println("Order was approved successfully");
                }
                case 2 -> {
                    orderService.refuse(order);
                    System.out.println("Order was refused successfully");
                }

            }
        } else {
            System.out.println("Invalid ID");
        }
        show();
    }

    @Override
    public void exit() {
        new AdminMainMenu().show();
    }
}
