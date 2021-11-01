package service;

import dao.UserDao;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDao();
    }

    @Override
    public User login(String username, String password) {
        List<User> users = userDao.getAll();
        return users.stream().filter(existedUser -> existedUser.getLogin().equals(username)
                && existedUser.getPassword().equals(password)).findFirst().orElse(null);
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

    @Override
    public void unblockUser(String login) {
        User user = userDao.getByLogin(login);
        if (user != null) {
            if (!user.isBlock()) {
                System.out.println("User " + user.getLogin() + " unblocked");
                user.setBlock(false);
            } else {
                System.out.println("User " + user.getLogin() + " is already unblocked");
            }
            userDao.update(user);
        } else {
            System.out.println("User not exists");
        }
    }

    @Override
    public void blockUser(String login) {
        User user = userDao.getByLogin(login);
        if (user != null) {
            if (user.isBlock()) {
                System.out.println("User " + user.getLogin() + " blocked");
                user.setBlock(true);
            } else {
                System.out.println("User " + user.getLogin() + " is already blocked");
            }
            userDao.update(user);
        } else {
            System.out.println("User not exists");
        }
    }
}
