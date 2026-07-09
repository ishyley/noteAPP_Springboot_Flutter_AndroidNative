package com.example.noteapp.note.controller;

import com.example.noteapp.note.dto.request.NoteRequest;
import com.example.noteapp.note.dto.response.NoteResponse;
import com.example.noteapp.note.model.Note;
import com.example.noteapp.note.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/getallnote")
    public List<NoteResponse> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/createnote")
    public NoteResponse createNote(@RequestBody NoteRequest request) {
        return noteService.createNote(request);
    }

    @PutMapping("/{id}")
    public Note updateNote(
            @PathVariable Long id,
            @RequestBody NoteRequest request) {

        return noteService.updateNote(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
    }
}
