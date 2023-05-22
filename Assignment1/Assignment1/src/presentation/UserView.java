package presentation;

import model.User;

import javax.swing.*;

public class UserView extends JFrame {
    private JTable notesTable;
    private JButton createNoteButton;
    private JButton deleteNoteButton;
    private JButton editNoteButton;
    private JButton logoutButton;
    private JTextField idTextField;
    private JTextField descriptionTextField;
    private JPanel panel;
    private JTextField titleTextField;
    private JTextField usernameTextField;
    private JButton viewAllNotesButton;

    public UserView() {
        super("Welcome!");
        setContentPane(panel);
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //frame on the center
        setVisible(true);

        UserController userController = new UserController(this);

        userController.logout(logoutButton);
        userController.createNote(createNoteButton);
        userController.editNote(editNoteButton);
        userController.deleteNote(deleteNoteButton);
        userController.viewAllNotesButton(viewAllNotesButton);
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(JTextField idTextField) {
        this.idTextField = idTextField;
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public void setDescriptionTextField(JTextField descriptionTextField) {
        this.descriptionTextField = descriptionTextField;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public void setTitleTextField(JTextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JTable getNotesTable() {
        return notesTable;
    }

    public void setNotesTable(JTable notesTable) {
        this.notesTable = notesTable;
    }
}
