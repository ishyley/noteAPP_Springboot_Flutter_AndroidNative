package com.example.noteapp.note.repository;

import com.example.noteapp.note.model.Note;
import com.example.noteapp.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
}
