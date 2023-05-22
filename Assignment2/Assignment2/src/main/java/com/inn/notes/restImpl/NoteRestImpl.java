package com.inn.notes.restImpl;

import com.inn.notes.constants.NotesConstants;
import com.inn.notes.rest.NoteRest;
import com.inn.notes.service.NoteService;
import com.inn.notes.utils.NotesUtils;
import com.inn.notes.wrapper.NoteWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NoteRestImpl implements NoteRest {

    @Autowired
    NoteService noteService;

    @Override
    public ResponseEntity<String> addNewNote(Map<String, String> requestMap) {
        try {
            return noteService.addNewNote(requestMap);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<NoteWrapper>> getAllNotes() {
        try {
            return noteService.getAllNotes();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateNote(Map<String, String> requestMap) {
        try {
            return noteService.updateNote(requestMap);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteNote(Integer id) {
        try {
            return noteService.deleteNote(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return noteService.updateStatus(requestMap);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<NoteWrapper>> getByCategory(Integer id) {
        try {
            return noteService.getByCategory(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<NoteWrapper> getById(Integer id) {
        try {
            return noteService.getById(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new NoteWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
