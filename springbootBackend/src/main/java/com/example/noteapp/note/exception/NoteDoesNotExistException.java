package com.example.noteapp.note.exception;

public class NoteDoesNotExistException extends RuntimeException {
    public NoteDoesNotExistException(String message) {
        super(message);
    }
}
