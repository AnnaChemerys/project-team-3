package view.impl;

import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.Scanner;

public class BlockUnblockUserMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private String[] items = {"1. Block/Unblock user", "2. PM to user", "0. Exit"};
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        while (true) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter username");
                    scanner.nextLine();
                    String login = scanner.nextLine();
                    System.out.println("Press 1 to block the user\n" +
                            "Press 2 to unlock the user");
                    int blockUnblock = scanner.nextInt();
                    switch (blockUnblock) {
                        case 1:
                            userService.blockUser(login);
                            break;
                        case 2:
                            userService.unblockUser(login);
                            break;
                    }
                    new AdminMainMenu().show();
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
        new AdminMainMenu().show();
    }
}
