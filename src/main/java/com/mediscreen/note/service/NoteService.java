package com.mediscreen.note.service;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {


    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGenerator;


    public NoteService(NoteRepository noteRepository, ModelMapper modelMapper, SequenceGeneratorService sequenceGenerator) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
        this.sequenceGenerator = sequenceGenerator;
    }

    private static final Logger log = LogManager.getLogger(NoteService.class);



    /**
     Find a note by id.
     @param id The id of the note to retrieve.
     @return A {@link NoteDto} representing the note with the given id, or an HTTP 404 (Not Found) response if the note could not be found.
     @throws NoteNotFoundException if the note could not be found.
     */
    public NoteDto findNoteById(int id) throws NoteNotFoundException {

        log.debug("Finding note with id: {}", id);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        log.debug("Found note: {}", note);
        return modelMapper.map(note, NoteDto.class);
    }


    /**
     Get all notes.
     @return A list of {@link NoteDto} representing all notes.
     */
    public List<NoteDto> getAllNotes() {

        log.debug("Getting all notes");
        List<Note> notes = noteRepository.findAll();

        log.debug("Found notes: {}", notes);
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
    }


    /**
     Get all notes of a patient id.
     @return A list of {@link NoteDto} representing all notes of a patient id.
     */
    public List<NoteDto> getNotesByPatientId(int patientId) {
        log.debug("Getting notes by patientId: {}", patientId);
        List<Note> notes = noteRepository.findAll().stream()
                .filter(note -> note.getPatientId() == patientId)
                .collect(Collectors.toList());
        log.debug("Found notes: {}", notes);
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
    }


    /**
     Create a new note.
     @param noteDto A {@link NoteDto} representing the note to create.
     @return A {@link NoteDto} representing the created note.
     */
    public NoteDto createNote(NoteDto noteDto) {
        log.debug("Creating note: {}", noteDto);
        Note note = modelMapper.map(noteDto, Note.class);
        note.setId((int) sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
        note.setDate(LocalDate.now());
        note = noteRepository.save(note);
        log.debug("Created note: {}", note);
        return modelMapper.map(note, NoteDto.class);
    }


    /**
     Update an existing note.
     @param id The id of the note to update.
     @param noteDto A {@link NoteDto} representing the updated note information.
     @return A {@link NoteDto} representing the updated note.
     @throws NoteNotFoundException if the note could not be found.
     */
    public NoteDto updateNote(int id, NoteDto noteDto) throws NoteNotFoundException {

        log.debug("Updating note with id: {} and data: {}", id, noteDto);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        note.setDate(LocalDate.now());
        note.setPatientId(noteDto.getPatientId());
        note.setNote(noteDto.getNote());
        note = noteRepository.save(note);

        log.debug("Updated note: {}", note);
        return modelMapper.map(note, NoteDto.class);
    }


    /**
     Delete a note by id.
     @param id the id of the note to delete
     @throws NoteNotFoundException if the note with the given id does not exist
     */
    public void deleteNote(int id) throws NoteNotFoundException {

        log.debug("Processing delete note request for id: {}", id);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        noteRepository.delete(note);
        log.debug("Note with id: {} deleted successfully", id);
    }



}
