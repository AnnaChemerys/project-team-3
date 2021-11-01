package view.impl;

import dao.AbstractDao;
import dao.UserDao;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.Scanner;
import java.util.stream.Collectors;

public class BlockUnblockUserMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private final String[] items = {"1. Block/Unblock user", "2. PM to user", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> blockUnblockUser();
                case 2 -> messages();
                case 0 -> exit();
            }
        }
    }

    private void messages() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void blockUnblockUser() {
        AbstractDao<User> userDao = new UserDao();
        System.out.println("Enter username");
        scanner.nextLine();
        String login = scanner.nextLine();
        if(userDao.getAll().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)){
            System.out.println(userDao.getAll().stream().map(User::getLogin).filter(x -> x.equals(login)).findFirst());
            System.out.println("Press 1 to block the user\n" +
                    "Press 2 to unlock the user");
            int blockUnblock = scanner.nextInt();
            switch (blockUnblock) {
                case 1 -> userService.blockUser(login);
                case 2 -> userService.unblockUser(login);
            }
        } else {
            System.out.println("Invalid login");
        }
        show();
    }
    @Override
    public void exit() {
        new AdminMainMenu().show();
    }
}
