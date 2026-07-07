package com.example.noteapp.note.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_topic")
    private String noteTopic;

    @Column(name = "note_content")
    private String noteContent;

    @Column(name = "note_created_at")
    private LocalDateTime noteCreatedAt;

    @Column(name = "note_updated_at")
    private LocalDateTime noteUpdatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Note(Long id, String noteTopic, String noteContent, LocalDateTime noteCreatedAt, LocalDateTime noteUpdatedAt, User user) {
        this.id = id;
        this.noteTopic = noteTopic;
        this.noteContent = noteContent;
        this.noteCreatedAt = noteCreatedAt;
        this.noteUpdatedAt = noteUpdatedAt;
        this.user = user;
    }

    public Note() {
    }

    @PrePersist
    public void prePersist() {
        noteCreatedAt = LocalDateTime.now();
        noteUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        noteUpdatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteTopic() {
        return noteTopic;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public LocalDateTime getNoteCreatedAt() {
        return noteCreatedAt;
    }

    public void setNoteCreatedAt(LocalDateTime noteCreatedAt) {
        this.noteCreatedAt = noteCreatedAt;
    }

    public LocalDateTime getNoteUpdatedAt() {
        return noteUpdatedAt;
    }

    public void setNoteUpdatedAt(LocalDateTime noteUpdatedAt) {
        this.noteUpdatedAt = noteUpdatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

//@Entity
//@Table(name = "notedb")
//public class Note {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long noteId;
//    @Column(name = "note_topic")
//    private String noteTopic;
//    @Column(name = "note_content", columnDefinition = "TEXT")
//    private String noteBody;
//    @Column(name = "note_created_at")
//    private LocalDateTime note_created_at;
//    @Column(name = "note_updated_at")
//    private LocalDateTime note_updated_at;
//
//    public Note(Long noteId, String noteTopic, String noteBody, LocalDateTime note_created_at, LocalDateTime note_updated_at) {
//        this.noteId = noteId;
//        this.noteTopic = noteTopic;
//        this.noteBody = noteBody;
//        this.note_created_at = note_created_at;
//        this.note_updated_at = note_updated_at;
//    }
//
//    public Note() {
//    }
//
//    @PrePersist
//    protected void onCreate() {
//        note_created_at = LocalDateTime.now();
//        note_updated_at = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        note_updated_at = LocalDateTime.now();
//    }
//
//    public Long getNoteId() {
//        return noteId;
//    }
//
//    public void setNoteId(Long noteId) {
//        this.noteId = noteId;
//    }
//
//    public String getNoteTopic() {
//        return noteTopic;
//    }
//
//    public void setNoteTopic(String noteTopic) {
//        this.noteTopic = noteTopic;
//    }
//
//    public String getNoteBody() {
//        return noteBody;
//    }
//
//    public void setNoteBody(String noteBody) {
//        this.noteBody = noteBody;
//    }
//
//    public LocalDateTime getNote_created_at() {
//        return note_created_at;
//    }
//
//    public void setNote_created_at(LocalDateTime note_created_at) {
//        this.note_created_at = note_created_at;
//    }
//
//    public LocalDateTime getNote_updated_at() {
//        return note_updated_at;
//    }
//
//    public void setNote_updated_at(LocalDateTime note_updated_at) {
//        this.note_updated_at = note_updated_at;
//    }
//}
