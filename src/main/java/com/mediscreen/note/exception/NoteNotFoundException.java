package com.mediscreen.note.exception;

public class NoteNotFoundException extends Exception {


    /**
     * Defines a custom exception named NoteNotFoundException.
     * Indicates that a note with the given id does not exist returning a string.
     * @param id id of the patient
     */
    public NoteNotFoundException(int id) {

        super(" Note does not exist with id : " + id);
    }
}
