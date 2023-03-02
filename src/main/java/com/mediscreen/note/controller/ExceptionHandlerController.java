package com.mediscreen.note.controller;

import com.mediscreen.note.exception.NoteNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {


    private static final Logger log = LogManager.getLogger(ExceptionHandlerController.class);

    /**
     * Handle NoteNotFoundException response entity.
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<String> handleNoteNotFoundException(NoteNotFoundException ex) {
        log.error("NoteNotFoundException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
