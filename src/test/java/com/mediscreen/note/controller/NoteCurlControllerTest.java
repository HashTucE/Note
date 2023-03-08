package com.mediscreen.note.controller;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NoteCurlControllerTest {

    @InjectMocks
    private NoteCurlController noteCurlController;
    @Mock
    private NoteService noteService;


    @Test
    void testAddPatient() {

        // Arrange
        int patId = 1;
        String note = "note";

        // Act
        ResponseEntity<String> response = noteCurlController.addPatientNote(patId, note);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patient note added", response.getBody());
        verify(noteService, Mockito.times(1)).createNote(any(NoteDto.class));
    }
}
