package com.example.noteapp.note.repository;

import com.example.noteapp.note.model.Note;
import com.example.noteapp.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByIdAndUser(Long id, User user);

    // Optional<Note> fetchAllNote(Long id, User user);

    Optional<Note> deleteByIdAndUser(Long id, User user);

    List<Note> findByUser(User user);
}
