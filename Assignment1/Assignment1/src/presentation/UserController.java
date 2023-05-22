package presentation;

import bll.NoteBLL;
import model.Note;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserController {

    private UserView userView;
    private LoginView loginView;
    private NoteBLL noteBLL;
    private String title;
    private String description;
    private int idNote;
    private String username;

    public UserController(UserView userView) {
        this.userView = userView;
        this.noteBLL = new NoteBLL();
    }

    public void getNoteInfo() {
        this.idNote = Integer.parseInt(userView.getIdTextField().getText());
        this.username = userView.getUsernameTextField().getText();
        this.title = userView.getTitleTextField().getText();
        this.description = userView.getDescriptionTextField().getText();
    }

    public void createNote(JButton createNoteButton) {
        createNoteButton.addActionListener(e -> {
            getNoteInfo();
            int result = noteBLL.insertNote(new Note(idNote, username, title, description));
            if (result == -1) {
                JFrame popup = new JFrame();
                JOptionPane.showMessageDialog(popup,
                        "Note addition failed due to invalid user data");
            } else {
                JFrame registerComplete = new JFrame();
                JOptionPane.showMessageDialog(registerComplete,
                        "New note added!");
            }
        });
    }

    public void editNote(JButton editButton) {
        editButton.addActionListener(e -> {
            getNoteInfo();
            noteBLL.update(new Note(idNote, username, title, description), idNote);

            JFrame newNote = new JFrame();
            JOptionPane.showMessageDialog(newNote,
                    "Note edited successfully!");
        });
    }

    public void deleteNote(JButton deleteButton) {
        deleteButton.addActionListener(e -> {
            getNoteInfo();
            noteBLL.delete(idNote);

            JFrame newNote = new JFrame();
            JOptionPane.showMessageDialog(newNote,
                    "Note deleted successfully!");
        });
    }

    public void viewAllNotesButton(JButton viewAllNotesButton) {
        viewAllNotesButton.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel();
            JTable table = userView.getNotesTable();

            noteBLL.viewNotesByUser(model, table, username);
        });
    }

    public void logout(JButton logoutButton) {
        logoutButton.addActionListener(e -> {
            loginView = new LoginView();
            userView.setVisible(false);
        });
    }
}
