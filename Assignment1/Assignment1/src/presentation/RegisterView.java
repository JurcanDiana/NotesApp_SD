package presentation;

import javax.swing.*;

public class RegisterView extends JFrame {
    private JPanel panel;
    private JButton backToLoginButton;
    private JButton registerButton;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;

    public RegisterView() {
        super("Register Page");
        setContentPane(panel);
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //frame on the center
        setVisible(true);

        RegisterController registerController = new RegisterController(this);

        registerController.backToLogin(backToLoginButton);
        registerController.register(registerButton);
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public void setFirstNameTextField(JTextField firstNameTextField) {
        this.firstNameTextField = firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public void setLastNameTextField(JTextField lastNameTextField) {
        this.lastNameTextField = lastNameTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(JTextField emailTextField) {
        this.emailTextField = emailTextField;
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

    public JCheckBox getShowPasswordCheckBox() {
        return showPasswordCheckBox;
    }

    public void setShowPasswordCheckBox(JCheckBox showPasswordCheckBox) {
        this.showPasswordCheckBox = showPasswordCheckBox;
    }
}
