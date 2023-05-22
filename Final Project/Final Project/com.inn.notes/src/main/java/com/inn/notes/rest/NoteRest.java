package com.inn.notes.rest;

import com.inn.notes.wrapper.NoteWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/note")
public interface NoteRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewNote(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<NoteWrapper>> getAllNotes();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateNote(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteNote(@PathVariable Integer id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<NoteWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<NoteWrapper> getById(@PathVariable Integer id);

}
