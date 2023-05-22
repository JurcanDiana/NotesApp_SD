package presentation;

import bll.UserBLL;
import model.User;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicLong;

public class RegisterController {

    private RegisterView registerView;
    private LoginView loginView;
    private UserBLL userBLL;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int userID;
    private static AtomicLong counter = new AtomicLong(1);

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.userBLL = new UserBLL();
        showPassword(registerView.getShowPasswordCheckBox());
    }

    public void getRegisterInfo() {
        this.username = registerView.getUsernameTextField().getText();
        this.password = String.valueOf(registerView.getPasswordField().getPassword());
        this.lastName = registerView.getLastNameTextField().getText();
        this.firstName = registerView.getFirstNameTextField().getText();
        this.email = registerView.getEmailTextField().getText();
    }

    public void backToLogin(JButton backToLoginButton) {
        backToLoginButton.addActionListener(e -> {
            LoginView loginView = new LoginView();
            registerView.setVisible(false);
        });
    }

    public void register(JButton registerButton) {
        registerButton.addActionListener(e -> {

            getRegisterInfo();
            userID = (int) counter.getAndIncrement();
            String hashedPassword = hashPassword(password);
            int result = userBLL.insertUser(new User(userID, firstName, lastName, username, hashedPassword, email));
            if (result == -1) {
                JFrame popup = new JFrame();
                JOptionPane.showMessageDialog(popup,
                        "Register failed due to invalid user data");
            } else {
                JFrame registerComplete = new JFrame();
                JOptionPane.showMessageDialog(registerComplete,
                        "Register complete!");
            }
        });
    }


    public void showPassword(JCheckBox checkBox) {
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                registerView.getPasswordField().setEchoChar((char) 0);
            } else {
                registerView.getPasswordField().setEchoChar('\u2022'); // default value
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
}
