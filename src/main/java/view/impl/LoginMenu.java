package view.impl;


import dao.UserDaoImpl;
import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.Scanner;

public class LoginMenu implements Menu {

    private UserService userService;
    private String[] items = {"1.Login", "2.Register", "0. Exit"};
    private Scanner scanner;

    @Override
    public void show() {
        showItems(items);
        scanner = new Scanner(System.in);

        while (true) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    loginSubMenu(scanner);
                    break;
                case 2:
                    loginSubMenu(scanner);
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu(Scanner scanner) {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();
        UserDaoImpl userDao = new UserDaoImpl();
        UserServiceImpl userService = new UserServiceImpl(userDao);

        if (userService.login(login, password)) {
            new ProductMenu().show();
        } else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerSubMenu(Scanner scanner) {
        show(); //todo add impl
    }
}
