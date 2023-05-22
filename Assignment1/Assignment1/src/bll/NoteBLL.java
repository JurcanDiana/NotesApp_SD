package bll;

import bll.validators.EmailAddressValidator;
import bll.validators.NoteDescriptionValidator;
import bll.validators.NoteTitleValidator;
import bll.validators.Validator;
import dao.NoteDAO;
import model.Note;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class NoteBLL {

    List<Validator<Note>> validators;
    private NoteDAO noteDAO;
    
    public NoteBLL() {
        validators = new ArrayList<Validator<Note>>();
        validators.add((Validator<Note>) new NoteTitleValidator());
        validators.add((Validator<Note>) new NoteDescriptionValidator());

        noteDAO = new NoteDAO();
    }

    public Note findNoteById(int id) {
        Note note = noteDAO.findById(id);
        if(note == null) {
            throw new NoSuchElementException("The note with id "
                    + id + " could not be found");
        }
        return note;
    }

    public Note findNoteByNotename(String notename) {
        Note note = noteDAO.findByName(notename);
        if(note == null) {
            throw new NoSuchElementException("The note of the Note "
                    + notename + " could not be found");
        }
        return note;
    }

    public Note findNoteByTitle(String noteTitle) {
        Note note = noteDAO.findByName(noteTitle);
        if(note == null) {
            throw new NoSuchElementException("The note with title "
                    + noteTitle + " could not be found");
        }
        return note;
    }

    public ArrayList<Note> findAll() {
        ArrayList<Note> notes = noteDAO.findAll();
        if(notes == null) {
            throw new NoSuchElementException("The list of notes is empty!");
        }
        return notes;
    }

    public List<Note> findNotesByUserName(String username) {
        List<Note> notes = noteDAO.findByUserName(username);
        if(notes == null) {
            throw new NoSuchElementException("No notes found for user with username: " + username);
        }
        return notes;
    }


    public List<Note> selectAll() {
        return noteDAO.findAll();
    }

    public void viewAllNotes(DefaultTableModel model, JTable table) {

        String[] columns = {"noteID", "userID", "title", "description"};

        for(String column: columns) {
            model.addColumn(column);
        }

        List<Note> notes = selectAll();

        for (Note note: notes) {
            model.addRow(new Object[]{String.valueOf(note.getId()),
                    String.valueOf(note.getUsername()),
                    String.valueOf(note.getTitle()),
                    String.valueOf(note.getDescription()),
            });
        }
        table.setModel(model);
    }

    public void viewNotesByUser(DefaultTableModel model, JTable table, String username) {

        String[] columns = {"noteID", "userID", "title", "description"};

        for(String column: columns) {
            model.addColumn(column);
        }

        List<Note> notes = findNotesByUserName(username);

        for (Note note: notes) {
            model.addRow(new Object[]{String.valueOf(note.getId()),
                    String.valueOf(note.getUsername()),
                    String.valueOf(note.getTitle()),
                    String.valueOf(note.getDescription()),
            });
        }
        table.setModel(model);
    }

    public int insertNote(Note note) {
        try {
            checkValidators(note);
            return noteDAO.insert(note);
        } catch (IllegalArgumentException e) {
            JFrame popup = new JFrame();
            JOptionPane.showMessageDialog(popup, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }


    public boolean update(Note note, int id) {
        checkValidators(note);
        return noteDAO.update(note, id);
    }

    public boolean delete(int id) {
        return noteDAO.delete(id);
    }

    public boolean checkValidators(Note note) {
        for (Validator<Note> validator: validators) {
            if(!validator.validate(note)) {
                return false;
            }
        }
        return true;
    }
}
