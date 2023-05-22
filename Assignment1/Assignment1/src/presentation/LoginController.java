package presentation;

import bll.UserBLL;
import model.User;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public class LoginController {

    private LoginView loginView;
    private RegisterView registerView;
    private AdminView adminView;
    private UserView userView;
    private UserBLL userBLL;
    private String username;
    private String password;
    private String hashedPassword;
    private static final String ADMIN = "admin";

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.userBLL = new UserBLL();
        showPassword(loginView.getCheckBox());
    }

    public void getLoginInfo() {
        this.username = loginView.getUsernameTextField().getText();
        this.password = String.valueOf(loginView.getPasswordField().getPassword());
        this.hashedPassword = hashPassword(password);
    }

    public void loginUser(JButton loginButton) {
        loginButton.addActionListener(e -> {
            getLoginInfo();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Please enter a valid username and password.");
            } else if (username.equals(ADMIN) && password.equals(ADMIN)) {
                adminView = new AdminView();
                adminView.setVisible(true);
                loginView.setVisible(false);
            } else {
                try {
                    User user = userBLL.findByName(username);
                    if (user.getPassword().equals(hashedPassword)) {
                        userView = new UserView();
                        userView.setVisible(true);
                        loginView.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Incorrect password!");
                    }
                } catch (NoSuchElementException ex) {
                    JOptionPane.showMessageDialog(loginView, "Account does not exist!");
                }
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showPassword(JCheckBox checkBox) {
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                loginView.getPasswordField().setEchoChar((char) 0);
            } else {
                loginView.getPasswordField().setEchoChar('\u2022'); // default value
            }
        });
    }

    public void register(JButton registerButton) {
        registerButton.addActionListener(e -> {
            registerView = new RegisterView();
            loginView.setVisible(false);
        });
    }
}
