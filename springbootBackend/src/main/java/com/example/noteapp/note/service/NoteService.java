package com.example.noteapp.note.service;

import com.example.noteapp.note.model.Note;
import com.example.noteapp.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNote(){
        return noteRepository.findAll();
    }

    public Note saveNote(Note note){
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id){
        noteRepository.deleteById(id);
    }
}
