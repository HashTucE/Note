package com.mediscreen.note.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "notes")
public class Note {


    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private int id;

    @NotNull(message = "Id of the patient is mandatory")
    @Field
    private int patientId;


    @NotBlank(message = "Note is mandatory")
    @Field
    private String note;

    @Field
    private LocalDate date;


    public Note() {
    }

    public Note(int patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


