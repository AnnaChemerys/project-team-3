package view.impl;

import dao.UserDao;
import model.User;
import view.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UsersMenu implements Menu {
    private final String[] items = new String[]{"1. Users list", "2. Block/ unblock user", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showAllUsers();
                case 2 -> new BlockUnblockUserMenu().show();
                case 0 -> exit();
            }
        }
    }

    private void showAllUsers() {
        UserDao userDao = new UserDao();
        List<User> userList = userDao.getAll().stream().filter(x -> x.getRole() == User.UserRole.USER).collect(Collectors.toList());
        if(userList.size() <= 0){
            System.out.println("---No users---");
        } else {
            System.out.println("Users list:");
            userList.forEach(x -> System.out.println("\t" + x));
        }
        show();
    }

    @Override
    public void exit() {
        new UserMainMenu().show();
    }
}
