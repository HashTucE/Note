package com.mediscreen.note.controller;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {



    @Mock
    private NoteService noteService;
    @InjectMocks
    private NoteController noteController;


    @Test
    @DisplayName("Should assert equality")
    void findNoteByIdTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        NoteDto note = new NoteDto();
        when(noteService.findNoteById(id)).thenReturn(note);

        // Act
        ResponseEntity<NoteDto> response = noteController.findNoteById(id);

        // Assert
        assertEquals(note, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(noteService).findNoteById(id);
    }


    @Test
    @DisplayName("Should throw NoteNotFoundException")
    void findNoteByIdNegativeTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        when(noteService.findNoteById(id)).thenThrow(NoteNotFoundException.class);

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> noteController.findNoteById(id));
        verify(noteService).findNoteById(id);
    }


    @Test
    @DisplayName("Should assert equality")
    void getAllNotesTest() {

        // Arrange
        List<NoteDto> notes = Collections.singletonList(new NoteDto());
        when(noteService.getAllNotes()).thenReturn(notes);

        // Act
        ResponseEntity<List<NoteDto>> response = noteController.getAllNotes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notes, response.getBody());
    }


    @Test
    @DisplayName("Should assert equality")
    void getNotesByPatientIdTest() {

        // Arrange
        int patientId = 1;
        NoteDto note1 = new NoteDto();
        NoteDto note2 = new NoteDto();
        List<NoteDto> notes = Arrays.asList(note1, note2);
        when(noteService.getNotesByPatientId(eq(patientId))).thenReturn(notes);

        // Act
        ResponseEntity<List<NoteDto>> result = noteController.getNotesByPatientId(patientId);

        // Assert
        assertEquals(ResponseEntity.ok(notes), result);
    }


    @Test
    @DisplayName("Should found equality")
    void validateNoteTest() {

        // Arrange
        NoteDto noteDto = new NoteDto();
        NoteDto note = new NoteDto();

        // Act
        when(noteService.createNote(noteDto)).thenReturn(note);
        ResponseEntity<NoteDto> response = noteController.validateNote(noteDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note, response.getBody());
    }


    @Test
    @DisplayName("Should found equality")
    void updateNoteTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        NoteDto noteDto = new NoteDto();
        NoteDto note = new NoteDto();

        // Act
        when(noteService.updateNote(id, noteDto)).thenReturn(note);
        ResponseEntity<NoteDto> response = noteController.updateNote(id, noteDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note, response.getBody());
        verify(noteService, times(1)).updateNote(id, noteDto);
    }


    @Test
    @DisplayName("Should throw NoteNotFoundException")
    void updateNoteNegativeTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        NoteDto noteDto = new NoteDto();
        when(noteService.updateNote(id, noteDto)).thenThrow(new NoteNotFoundException(1));

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> noteController.updateNote(id, noteDto));
        verify(noteService, times(1)).updateNote(id, noteDto);
    }


    @Test
    @DisplayName("Should found equality")
    void deleteNoteTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        doNothing().when(noteService).deleteNote(id);

        // Act
        ResponseEntity<Void> response = noteController.deleteNote(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noteService, times(1)).deleteNote(id);
    }


    @Test
    @DisplayName("Should throw NoteNotFoundException")
    void deleteNoteNegativeTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        doThrow(new NoteNotFoundException(id)).when(noteService).deleteNote(id);

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> noteController.deleteNote(id));
        verify(noteService, times(1)).deleteNote(id);
    }
}
