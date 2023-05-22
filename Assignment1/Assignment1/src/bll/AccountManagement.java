package bll;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountManagement {
    private List<User> registeredUsers;

    public AccountManagement() {
        this.registeredUsers = new ArrayList<>();
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void displayUsers() {
        for(User user: registeredUsers) {
            System.out.print("\nUsername: " + user.getUsername());
        }
    }

}
