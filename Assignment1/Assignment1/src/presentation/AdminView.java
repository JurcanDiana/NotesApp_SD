package presentation;

import javax.swing.*;

public class AdminView extends JFrame {
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton viewAllUsersButton;
    private JButton viewAllNotesButton;
    private JButton logoutButton;
    private JPanel panel;
    private JTable table;
    private JTextField idTextField;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;

    public AdminView() {
        super("Admin Page");
        setContentPane(panel);
        setSize(820, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //frame on the center
        setVisible(true);

        AdminController adminController = new AdminController(this);

        adminController.logout(logoutButton);
        adminController.addUserButton(addUserButton);
        adminController.editUserButton(editUserButton);
        adminController.deleteUserButton(deleteUserButton);
        adminController.viewAllUsersButton(viewAllUsersButton);
        adminController.viewAllNotesButton(viewAllNotesButton);
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(JTextField idTextField) {
        this.idTextField = idTextField;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(JTextField emailTextField) {
        this.emailTextField = emailTextField;
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

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
