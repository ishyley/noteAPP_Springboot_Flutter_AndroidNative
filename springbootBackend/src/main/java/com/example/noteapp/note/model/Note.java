package com.example.noteapp.note.model;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String noteId;
    @Column(name = "note_topic")
    private String noteTopic;
    @Column(name = "note_content", columnDefinition = "TEXT")
    private String noteBody;
    @Column(name = "note_created_at", updatable = true)
    private LocalDateTime note_created_at;
    @Column(name = "note_updated_at")
    private LocalDateTime note_updated_at;

    public Note(String noteId, String noteTopic, String noteBody, LocalDateTime note_created_at, LocalDateTime note_updated_at) {
        this.noteId = noteId;
        this.noteTopic = noteTopic;
        this.noteBody = noteBody;
        this.note_created_at = note_created_at;
        this.note_updated_at = note_updated_at;
    }

    @PrePersist
    protected void onCreate(){
        note_created_at = LocalDateTime.now();
        note_updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        note_updated_at = LocalDateTime.now();
    }

    public Note() {
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTopic() {
        return noteTopic;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public LocalDateTime getNote_created_at() {
        return note_created_at;
    }

    public void setNote_created_at(LocalDateTime note_created_at) {
        this.note_created_at = note_created_at;
    }

    public LocalDateTime getNote_updated_at() {
        return note_updated_at;
    }

    public void setNote_updated_at(LocalDateTime note_updated_at) {
        this.note_updated_at = note_updated_at;
    }
}
