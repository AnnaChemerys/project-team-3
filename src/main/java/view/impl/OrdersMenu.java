package view.impl;

import dao.AbstractDao;
import dao.OrderDao;
import model.Order;
import service.OrderService;
import service.OrderServiceImpl;
import view.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrdersMenu implements Menu {
    private final OrderService orderService = new OrderServiceImpl();
    private final OrderDao orderDao = new OrderDao();
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
        if (orderDao.getAll().stream().filter(x -> !x.isApproved()).map(Order::getId).collect(Collectors.toList()).contains(id)) {
            //noinspection OptionalGetWithoutIsPresent
            Order order = orderDao.getAll().stream().filter(x -> x.getId().equals(id)).findFirst().get();
            System.out.println(order);
            System.out.println("Press 1 to approve order\n" +
                    "Press 2 to refuse order");
            int approveOrder = scanner.nextInt();
            switch (approveOrder) {
                case 1 -> orderService.approve(order);
                case 2 -> orderService.refuse(order);
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
