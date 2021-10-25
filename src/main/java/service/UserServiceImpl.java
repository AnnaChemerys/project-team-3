package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean login(String username, String password) {
        List<User> users = userDao.getAll();
        User user = new User(username, password);
        if (users.contains(user)) {
            System.out.println("Welcome");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void register(User user) {
        if (validateUser(user)) {
            userDao.save(user);
        } else {
            System.out.println("Error");
        }
    }

    public boolean validateUser(User user) {
        List<User> users = userDao.getAll();
        if (users.stream().anyMatch(existedUser -> existedUser.equals(user.getLogin()))) {
            System.out.println("User with login - " + user.getLogin() + " already exists!");
            return false;
        }
        return true;
    }
}