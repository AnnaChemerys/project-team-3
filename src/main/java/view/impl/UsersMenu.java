package view.impl;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UsersMenu implements Menu {
    private final UserDao userDao = new UserDaoImpl();
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
                case 2 -> blockUnblockUser();
                case 0 -> exit();
            }
        }
    }

    private void blockUnblockUser() {
        System.out.print("Choose user login: ");
        String login = scanner.nextLine();
        if(userDao.getAll().stream().map(User::getLogin).collect(Collectors.toList()).contains(login)){
            System.out.println(userDao.getByLogin(login));
            System.out.println("-----Blocking/Unblocking users-----");
        } else {
            System.out.println("Invalid login");
        }
        show();
    }

    private void showAllUsers() {
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
