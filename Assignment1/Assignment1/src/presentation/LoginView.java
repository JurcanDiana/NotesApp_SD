package presentation;

import javax.swing.*;

public class LoginView extends JFrame {

    private JPanel panel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JCheckBox checkBox;

    public LoginView() {
        super("Login Page");
        setContentPane(panel);
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //frame on the center
        setVisible(true);

        LoginController loginController = new LoginController(this);

        loginController.register(registerButton);
        loginController.loginUser(loginButton);
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
