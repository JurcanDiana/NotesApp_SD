package com.inn.notes.service;

import com.inn.notes.wrapper.NoteWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface NoteService {

    ResponseEntity<String> addNewNote(Map<String, String> requestMap);

    ResponseEntity<List<NoteWrapper>> getAllNotes();

    ResponseEntity<String> updateNote(Map<String, String> requestMap);

    ResponseEntity<String> deleteNote(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<NoteWrapper>> getByCategory(Integer id);

    ResponseEntity<NoteWrapper> getById(Integer id);
}
