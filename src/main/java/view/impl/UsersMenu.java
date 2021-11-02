package view.impl;

import model.User;
import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UsersMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private final String[] items = new String[]{"1. Users list", "2. Block/ unblock user", "3. PM to user", "0. Exit"};
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
                case 2 -> blockUnblockUser();
                case 3 -> messages();
                case 0 -> exit();
            }
        }
    }

    private void messages() {
        System.out.println("Temporarily unavailable");
        show();
    }

    private void showAllUsers() {
        List<User> userList = userService.getAll().stream().filter(x -> x.getRole() == User.UserRole.USER).collect(Collectors.toList());
        if (userList.size() <= 0) {
            System.out.println("---No users---");
        } else {
            System.out.println("Users list:");
            userList.forEach(x -> System.out.println("\t" + x));
        }
        show();
    }

    private void blockUnblockUser() {
        System.out.println("Enter username");
        String login = scanner.nextLine();
        if (userService.getAll().stream().filter(x -> x.getRole() == User.UserRole.USER).map(User::getLogin).collect(Collectors.toList()).contains(login)) {
            //noinspection OptionalGetWithoutIsPresent
            System.out.println(userService.getAll().stream().filter(x -> x.getLogin().equals(login)).findFirst().get());
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
