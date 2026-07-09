package com.example.noteapp.note.dto.response;

public class NoteResponse {

    private Long id;
    private String noteTopic;
    private String noteContent;

    public NoteResponse() {
    }

    public NoteResponse(Long id, String noteTopic, String noteContent) {
        this.id = id;
        this.noteTopic = noteTopic;
        this.noteContent = noteContent;
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

    @Override
    public String toString() {
        return "NoteResponse [id=" + id + ", noteTopic=" + noteTopic + ", noteContent=" + noteContent + "]";
    }

}
