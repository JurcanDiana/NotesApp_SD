package presentation;

import bll.NoteBLL;
import bll.UserBLL;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AdminController {

    private AdminView adminView;
    private UserBLL userBLL;
    private NoteBLL noteBLL;
    private LoginView loginView;
    private int idUser;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;':\"<>,.?/";
    private static final int LENGTH = 8;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
        this.userBLL = new UserBLL();
        this.noteBLL = new NoteBLL();
    }

    public void getUserInfo() {
        this.idUser = Integer.parseInt(adminView.getIdTextField().getText());
        this.username = adminView.getUsernameTextField().getText();
        this.firstName = adminView.getFirstNameTextField().getText();
        this.lastName = adminView.getLastNameTextField().getText();
        this.email = adminView.getEmailTextField().getText();
    }

    public void addUserButton(JButton addUserButton) {
        addUserButton.addActionListener(e -> {
            getUserInfo();
            password = hashPassword(generatePassword(LENGTH));
            userBLL.insertUser(new User(idUser, firstName, lastName, username, password, email));

            JFrame newUser = new JFrame();
            JOptionPane.showMessageDialog(newUser,
                    "New user added!");
        });
    }

    public void editUserButton(JButton editUserButton) {
        editUserButton.addActionListener(e -> {
            getUserInfo();
            password = hashPassword(generatePassword(LENGTH));
            userBLL.update(new User(idUser, firstName, lastName, username, password, email), idUser);

            JFrame newUser = new JFrame();
            JOptionPane.showMessageDialog(newUser,
                    "User edited successfully!");
        });
    }

    public void deleteUserButton(JButton deleteUserButton) {
        deleteUserButton.addActionListener(e -> {
            getUserInfo();
            userBLL.delete(idUser);

            JFrame newUser = new JFrame();
            JOptionPane.showMessageDialog(newUser,
                    "User deleted successfully!");
        });
    }

    public void viewAllUsersButton(JButton viewAllUsersButton) {
        viewAllUsersButton.addActionListener(e ->{
            DefaultTableModel model = new DefaultTableModel();
            JTable table = adminView.getTable();

            userBLL.viewAllUsers(model, table);
        });
    }

    public void viewAllNotesButton(JButton viewAllNotesButton) {
        viewAllNotesButton.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel();
            JTable table = adminView.getTable();

            noteBLL.viewAllNotes(model, table);
        });
    }

    public void logout(JButton logoutButton) {
        logoutButton.addActionListener(e -> {
            loginView = new LoginView();
            adminView.setVisible(false);
        });
    }

    public static String generatePassword(int length) {
        String password = "";
        Random random = new Random();

        password += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        password += NUMBERS.charAt(random.nextInt(NUMBERS.length()));
        password += SYMBOLS.charAt(random.nextInt(SYMBOLS.length()));

        for (int i = 0; i < length - 3; i++) {
            int type = random.nextInt(3);
            if (type == 0) {
                password += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            } else if (type == 1) {
                password += NUMBERS.charAt(random.nextInt(NUMBERS.length()));
            } else {
                password += SYMBOLS.charAt(random.nextInt(SYMBOLS.length()));
            }
        }

        char[] chars = password.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = chars[index];
            chars[index] = chars[i];
            chars[i] = temp;
        }

        return new String(chars);
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
