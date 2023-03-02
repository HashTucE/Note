package com.mediscreen.note.controller;

import com.mediscreen.note.exception.NoteNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerControllerTest {


    @InjectMocks
    private ExceptionHandlerController exceptionHandlerController;


    @Test
    public void handleNoteNotFoundExceptionTest() {

        NoteNotFoundException ex = new NoteNotFoundException(1);
        ResponseEntity<String> response = exceptionHandlerController.handleNoteNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(" Note does not exist with id : 1", response.getBody());
    }
}
