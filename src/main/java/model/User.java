package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable, HasId {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private String login;
    private String password;
    private UserRole role;


    private boolean isBlock;


    public enum UserRole {USER, ADMIN}

    public User() {
        this.id = UUID.randomUUID().toString();
    }


    public User(String login, String password, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.login = login;
        this.password = password;
        this.role = role;
        this.isBlock = false;
    }

    public String getId() {
        return id;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isBlock == user.isBlock && Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, isBlock);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isBlock=" + isBlock +
                '}';
    }

}


