package view.impl;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.CurrentUser;
import view.Menu;

import java.util.Scanner;

public class LoginMenu implements Menu {
    private final UserService userService = new UserServiceImpl();
    private final String[] items = {"1.Login", "2.User registration", "3.Administrator registration", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> loginSubMenu();
                case 2 -> registerUser();
                case 3 -> registerAdmin();
                case 0 -> exit();
            }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu() {
        UserDao userDao = new UserDaoImpl();
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();

        if (userService.login(login, password)) {
            CurrentUser.user = userDao.getByLogin(login);
            System.out.println("Successfully authorization");
            new UserMainMenu().show();
        } else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerUser() {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = new User(login, password, User.UserRole.USER);
        userService.register(user);
        show();
    }

    private void registerAdmin() {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = new User(login, password, User.UserRole.ADMIN);
        userService.register(user);
        show();
    }
}
