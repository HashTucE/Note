package com.mediscreen.note.controller;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {


    private final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }



    private static final Logger log = LogManager.getLogger(NoteController.class);



    /**
     * Find note by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws NoteNotFoundException the note not found exception
     */
    @GetMapping("/find/{id}")
    @Operation(summary = "Find note by ID", description = "Retrieves a note for a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note retrieved successfully",
                    content = @Content(schema = @Schema(implementation = NoteDto.class))),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<NoteDto> findNoteById(@PathVariable int id) throws NoteNotFoundException {
        log.debug("Find note by id request received, id: {}", id);
        NoteDto note = noteService.findNoteById(id);
        log.debug("Find note by id request processed, note: {}", note);
        return ResponseEntity.ok(note);
    }


    /**
     * Get all notes response entity.
     *
     * @return the response entity
     */
    @GetMapping("/list")
    @Operation(summary = "Get all notes", description = "Retrieves a list of all notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoteDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        log.debug("Get all notes request received");
        List<NoteDto> noteDtoList = noteService.getAllNotes();
        log.debug("Get all notes request processed, notes count: {}", noteDtoList.size());
        return ResponseEntity.ok(noteDtoList);
    }


    /**
     * Get notes by patient id response entity.
     *
     * @param patientId the patient id
     * @return the response entity
     */
    @GetMapping("/list/{patientId}")
    @Operation(summary = "Get notes by patient ID", description = "Retrieves a list of notes for a given patient ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoteDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable int patientId) {
        log.debug("Get notes by patientId request received, patientId: {}", patientId);
        List<NoteDto> notesList = noteService.getNotesByPatientId(patientId);
        log.debug("Get notes by patientId request processed, notes count: {}", notesList.size());
        return ResponseEntity.ok(notesList);
    }


    /**
     * Validate note response entity.
     *
     * @param noteDto the note dto
     * @return the response entity
     */
    @PostMapping("/validate")
    @Operation(summary = "Validate note", description = "Validates and creates a new note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note created successfully",
                    content = @Content(schema = @Schema(implementation = NoteDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<NoteDto> validateNote(@Valid @RequestBody NoteDto noteDto) {
        log.debug("Validate note request received, note: {}", noteDto);
        NoteDto note = noteService.createNote(noteDto);
        log.debug("Validate note request processed, note: {}", note);
        return ResponseEntity.ok(note);
    }


    /**
     * Update note response entity.
     *
     * @param id the id
     * @param noteDto the note dto
     * @return the response entity
     * @throws NoteNotFoundException the note not found exception
     */
    @PostMapping("/update/{id}")
    @Operation(summary = "Update note", description = "Updates an existing note with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully",
                    content = @Content(schema = @Schema(implementation = NoteDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<NoteDto> updateNote(@PathVariable int id, @Valid @RequestBody NoteDto noteDto) throws NoteNotFoundException {
        log.debug("Update note request received, id: {}, note: {}", id, noteDto);
        NoteDto note = noteService.updateNote(id, noteDto);
        log.debug("Update note request processed, note: {}", note);
        return ResponseEntity.ok(note);
    }


    /**

     Delete a note by id.
     @param id The id of the note to delete.
     @return An HTTP 204 (No Content) response if the note was successfully deleted, or an HTTP 404 (Not Found) response if the note could not be found.
     @throws NoteNotFoundException if the note could not be found.
     */
    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete note", description = "Deletes an existing note with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Void> deleteNote(@PathVariable int id) throws NoteNotFoundException {
        noteService.deleteNote(id);
        log.debug("Deleted note with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
