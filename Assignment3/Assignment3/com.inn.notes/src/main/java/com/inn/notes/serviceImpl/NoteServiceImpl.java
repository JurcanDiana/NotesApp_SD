package com.inn.notes.serviceImpl;

import com.inn.notes.JWT.JwtFilter;
import com.inn.notes.POJO.Category;
import com.inn.notes.POJO.Note;
import com.inn.notes.constants.NotesConstants;
import com.inn.notes.dao.NoteDao;
import com.inn.notes.service.NoteService;
import com.inn.notes.utils.NotesUtils;
import com.inn.notes.wrapper.NoteWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    NoteDao noteDao;

    @Override
    public ResponseEntity<String> addNewNote(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateNoteMap(requestMap, false)) {
                    noteDao.save(getNoteFromMap(requestMap, false));
                    return NotesUtils.getResponseEntity("Note added successfully", HttpStatus.OK);
                }
                return NotesUtils.getResponseEntity(NotesConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return NotesUtils.getResponseEntity(NotesConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateNoteMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("title")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if(!validateId) {
                return true;
            }
        }
        return false;
    }

    private Note getNoteFromMap(Map<String, String> requestMap, boolean isAdd) {
        Note note = new Note();
        Category category = new Category();

        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        if(isAdd) {
            note.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            note.setStatus("true");
        }
        note.setCategory(category);
        note.setTitle(requestMap.get("title"));
        note.setDescription(requestMap.get("description"));

        return note;
    }

    @Override
    public ResponseEntity<List<NoteWrapper>> getAllNotes() {
        try {
            return new ResponseEntity<>(noteDao.getAllNotes(), HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateNote(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if (validateNoteMap(requestMap, true)) {
                    Optional<Note> optional = noteDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()) {
                        Note note = getNoteFromMap(requestMap, true);
                        note.setStatus(optional.get().getStatus());
                        noteDao.save(note);
                        return  NotesUtils.getResponseEntity("Note updated successfully!", HttpStatus.OK);
                    } else {
                        return NotesUtils.getResponseEntity("Note id doesn't exist.", HttpStatus.OK);
                    }
                } else {
                    return NotesUtils.getResponseEntity(NotesConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return NotesUtils.getResponseEntity(NotesConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteNote(Integer id) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optional = noteDao.findById(id);
                if(!optional.isEmpty()) {
                    noteDao.deleteById(id);
                    return NotesUtils.getResponseEntity("Note deleted successfully!", HttpStatus.OK);
                } else {
                    return NotesUtils.getResponseEntity("Note id doesn't exist.", HttpStatus.OK);
                }
            } else {
                return NotesUtils.getResponseEntity(NotesConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optional = noteDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()) {
                    noteDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return NotesUtils.getResponseEntity("Note status updated successfully!", HttpStatus.OK);
                } else {
                    return NotesUtils.getResponseEntity("Note id doesn't exist.", HttpStatus.OK);
                }
            } else {
                return NotesUtils.getResponseEntity(NotesConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<NoteWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(noteDao.getNoteByCategory(id), HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<NoteWrapper> getById(Integer id) {
        try {
            return new ResponseEntity<>(noteDao.getNoteById(id), HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new NoteWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
