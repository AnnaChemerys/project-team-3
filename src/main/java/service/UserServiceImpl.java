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
        return users.stream().anyMatch(existedUser -> existedUser.getLogin().equals(username) && existedUser.getPassword().equals(password));
    }

    public void register(User user) {
        if (validateUser(user)) {
            userDao.save(user);
        } else {
            System.out.println("Failed to register user. User with login " + user.getLogin() + " already exists!");
        }
    }

    @Override
    public boolean validateUser(User user) {
        List<User> users = userDao.getAll();
        return users.stream().noneMatch(existedUser -> existedUser.getLogin().equals(user.getLogin()));
    }
}