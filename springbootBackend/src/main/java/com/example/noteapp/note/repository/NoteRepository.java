package com.example.noteapp.note.repository;

import com.example.noteapp.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
