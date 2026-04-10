package com.example.noteapp.note.web;

import com.example.noteapp.note.model.Note;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @GetMapping("/")
    public  String hello(){
        return "Hello World";
    }

//        private Map<String, Note> db = new HashMap<>(){{
//            put("1", new Note("1", "hello", "hello are we okay today"));
//        }};

    @GetMapping("/notes")  //gives the list of all notes
    public Collection<Note> get(){
        return db.values();
    }

    @GetMapping("/notes/{noteId}")
    public Note get(@PathVariable String noteId){
        Note note = db.get(noteId);
        if (note == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return db.get(noteId);
    }

    @DeleteMapping("/notes/{noteId}")  //gives the list of all notes
    public void delete(@PathVariable String noteId){
        Note note = db.remove(noteId);
        if (note == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-note/")  //create note
    public Note create( Note note){

        note.setNoteId(UUID.randomUUID().toString());
        db.put(note.getNoteId() , note);
        return note;
    }











}
