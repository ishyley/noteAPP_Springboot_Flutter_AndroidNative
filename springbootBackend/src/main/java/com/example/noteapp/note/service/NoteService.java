package com.example.noteapp.note.service;

import com.example.noteapp.note.dto.request.NoteRequest;
import com.example.noteapp.note.dto.response.NoteResponse;
import com.example.noteapp.note.exception.NoteNotFoundException;
import com.example.noteapp.note.model.Note;
import com.example.noteapp.note.model.User;
import com.example.noteapp.note.repository.NoteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    private final User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (User) authentication.getPrincipal();
    }

    public List<NoteResponse> getAllNotes() {

        User user = getCurrentUser();

        return noteRepository.findByUser(user)
                .stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getNoteTopic(),
                        note.getNoteContent()))
                .toList();
    }

    public NoteResponse createNote(NoteRequest request) {

        User user = getCurrentUser();

        Note note = new Note();
        note.setNoteTopic(request.getNoteTopic());
        note.setNoteContent(request.getNoteContent());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        return new NoteResponse(
                savedNote.getId(),
                savedNote.getNoteTopic(),
                savedNote.getNoteContent());
    }

    public Note updateNote(Long id, NoteRequest request) {
        User user = getCurrentUser();

        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        note.setNoteTopic(request.getNoteTopic());
        note.setNoteContent(request.getNoteContent());
        note.setNoteUpdatedAt(LocalDateTime.now());

        return noteRepository.save(note);

    }

    public void deleteNoteById(Long id) {
        User user = getCurrentUser();

        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        noteRepository.delete(note);
    }
}
