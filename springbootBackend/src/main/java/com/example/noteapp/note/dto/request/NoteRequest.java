package com.example.noteapp.note.dto.request;

public class NoteRequest {

    private String noteTopic;

    private String noteContent;


    public NoteRequest() {
    }

    public NoteRequest(String noteContent, String noteTopic) {
        this.noteContent = noteContent;
        this.noteTopic = noteTopic;
    }

    @Override
    public String toString() {
        return "NoteRequest{" +
                "noteContent='" + noteContent + '\'' +
                ", noteTopic='" + noteTopic + '\'' +
                '}';
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteTopic() {
        return noteTopic;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }
}
