package com.mediscreen.note.service;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {


    @Mock
    private SequenceGeneratorService sequenceGenerator;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private NoteService noteService;


    @Test
    @DisplayName("Should Return NoteDto")
    public void findNoteByIdTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        Note note = new Note(1, "note");
        note.setId(id);
        NoteDto noteDto = new NoteDto(1, "note");
        noteDto.setId(id);
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        // Act
        NoteDto result = noteService.findNoteById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(1, result.getPatientId());
        assertEquals("note", result.getNote());
    }


    @Test
    @DisplayName("Should Throw NoteNotFoundException")
    public void findNoteByIdNegativeTest() {

        // Arrange
        int id = 1;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> {
            noteService.findNoteById(id);
        }, "Patient does not exist with id : 1");
    }


    @Test
    @DisplayName("should return all notes")
    void getAllNotesTest() {

        // Arrange
        Note note = new Note(1, "note");
        Note note2 = new Note(2, "note2");
        NoteDto patientDto1 = new NoteDto(1, "note");
        NoteDto patientDto2 = new NoteDto(2, "note2");

        when(noteRepository.findAll()).thenReturn(Arrays.asList(note, note2));
        when(modelMapper.map(note, NoteDto.class)).thenReturn(patientDto1);
        when(modelMapper.map(note2, NoteDto.class)).thenReturn(patientDto2);

        // Act
        List<NoteDto> result = noteService.getAllNotes();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getPatientId());
        assertEquals("note", result.get(0).getNote());
        assertEquals(2, result.get(1).getPatientId());
        assertEquals("note2", result.get(1).getNote());
        verify(noteRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Note.class), eq(NoteDto.class));
    }


    @Test
    @DisplayName("should return all notes from a patient")
    void getNotesByPatientIdTest() {

        // Arrange
        int patientId = 1;
        Note note1 = new Note();
        note1.setPatientId(patientId);
        Note note2 = new Note();
        note2.setPatientId(patientId);
        NoteDto noteDto1 = new NoteDto();
        NoteDto noteDto2 = new NoteDto();
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));
        when(modelMapper.map(note1, NoteDto.class)).thenReturn(noteDto1);
        when(modelMapper.map(note2, NoteDto.class)).thenReturn(noteDto2);

        // Act
        List<NoteDto> result = noteService.getNotesByPatientId(patientId);

        // Assert
        assertEquals(Arrays.asList(noteDto1, noteDto2), result);
    }


    @Test
    @DisplayName("should return no notes for a patient with no matching patientId")
    void getNotesByPatientTest2() {

        // Arrange
        int patientId = 1;
        Note note1 = new Note();
        note1.setPatientId(2);
        Note note2 = new Note();
        note2.setPatientId(3);
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        // Act
        List<NoteDto> result = noteService.getNotesByPatientId(patientId);

        // Assert
        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("should return created note")
    void createNoteTest() {

        // Arrange
        NoteDto noteDto = new NoteDto(1, "note");
        Note note = new Note(1, "note");
        when(noteRepository.save(note)).thenReturn(note);
        when(modelMapper.map(noteDto, Note.class)).thenReturn(note);
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);
        when(sequenceGenerator.generateSequence(anyString())).thenReturn(Long.valueOf(1));

        // Act
        NoteDto result = noteService.createNote(noteDto);

        // Assert
        assertEquals(noteDto, result);
        verify(noteRepository, times(1)).save(note);
        verify(modelMapper, times(1)).map(noteDto, Note.class);
        verify(modelMapper, times(1)).map(note, NoteDto.class);
    }


    @Test
    @DisplayName("should return updated note")
    void updateNoteTest() throws NoteNotFoundException {

        // Arrange
        int id = 1;
        NoteDto noteDto = new NoteDto(1, "note");
        Note note = new Note(1, "note");
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        // Act
        NoteDto result = noteService.updateNote(id, noteDto);

        // Assert
        assertEquals(noteDto, result);
        verify(noteRepository, times(1)).findById(id);
        verify(noteRepository, times(1)).save(note);
        verify(modelMapper, times(1)).map(note, NoteDto.class);
    }


    @Test
    @DisplayName("should throw NoteNotFoundException")
    void updateNoteNegativeTest() {

        // Arrange
        int id = 1;
        NoteDto noteDto = new NoteDto(1, "note");

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(id, noteDto));
        verify(noteRepository, times(1)).findById(id);
        verify(noteRepository, times(0)).save(any());
        verify(modelMapper, times(0)).map(any(), any());
    }


    @Test
    @DisplayName("should call note repository")
    public void deleteNote() throws NoteNotFoundException {

        // Arrange
        int noteId = 1;
        Note note = new Note();
        note.setId(noteId);
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        // Act
        noteService.deleteNote(noteId);

        // Assert
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).delete(note);
    }


    @Test
    @DisplayName("should throw NoteNotFoundException")
    public void deleteNoteNegativeTest() {

        // Arrange
        int noteId = 1;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNote(noteId));
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, never()).delete(any(Note.class));
    }
}
