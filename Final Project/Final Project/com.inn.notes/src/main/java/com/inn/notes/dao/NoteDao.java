package com.inn.notes.dao;

import com.inn.notes.POJO.Note;
import com.inn.notes.wrapper.NoteWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface NoteDao extends JpaRepository<Note, Integer> {

    List<NoteWrapper> getAllNotes();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    List<NoteWrapper> getNoteByCategory(@Param("id") Integer id);

    NoteWrapper getNoteById(@Param("id") Integer id);

}
