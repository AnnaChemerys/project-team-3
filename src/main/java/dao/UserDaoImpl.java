package dao;

import model.User;
import util.FileOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO
public class UserDaoImpl implements UserDao {

    private final String filename = "users.ser";
    private List<User> users;
    private final FileOperation fileOperation = new FileOperation();

    public UserDaoImpl() {
        List<Object> raw = fileOperation.readFromFile(filename);
        users = raw.stream()
                .map(u -> (User) u)
                .collect(Collectors.toList());
    }

    private void writeToFile(List<User> users) {
        List<Object> objects = users.stream()
                .map(u -> (Object) u)
                .collect(Collectors.toList());

        fileOperation.writeIntoFile(objects, filename);
    }

    @Override
    public void save(User user) {
        users.add(user);
        writeToFile(users);
    }

    @Override
    public void update(User user) {
        for (User userTemp : users) {
            if (userTemp.equals(user)) {
                userTemp.setRole(user.getRole());
                userTemp.setPassword(user.getPassword());
                writeToFile(users);
            }
        }
    }

    @Override
    public void delete(User user) {
        users.remove(user);
        writeToFile(users);
    }

    @Override
    public User getByLogin(String login) {
        for (User userTemp : users) {
            if (userTemp.getLogin().equals(login)) {
                return userTemp;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
