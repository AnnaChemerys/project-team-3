package view.impl;

import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.Scanner;

public class AdminMainMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private String[] items = {"1. Users menu", "2. Order menu", "3. Products menu", "0. Exit"};
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        while (true) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    new BlockUnblockUserMenu().show();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }


}
