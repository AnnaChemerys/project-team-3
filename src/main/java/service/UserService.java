package service;


import model.User;

import java.util.Arrays;
import java.util.List;

public interface UserService {

    /**
     * Used to login a user
     *
     * @param username user name
     * @param password user password
     * @return outcome of login - success or not
     */

    User login(String username, String password);

    void register(User user);

    boolean validateUser(User user);

    void blockUser(String login);

    void unblockUser(String login);

    List<User> getAll();
}
