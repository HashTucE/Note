package com.mediscreen.note.controller;

import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteCurlController {


    private static final Logger log = LogManager.getLogger(NoteCurlController.class);

    private final NoteService noteService;

    public NoteCurlController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Adds a new patient note
     * @param patId the ID of the patient
     * @param note the patient's note to add
     * @return ResponseEntity with a message indicating that the patient note has been added
     */
    @PostMapping("/patHistory/add")
    @Operation(summary = "Add a new patient note", description = "Endpoint for adding a new patient note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid parameter")
    })
    @Parameters({
            @Parameter(name = "patId", description = "Patient's ID", required = true, example = "1"),
            @Parameter(name = "e", description = "Patient's note", required = true, example = "Patient is in a good shape")
    })
    public ResponseEntity<String> addPatientNote(@RequestParam("patId") int patId,
                                                 @RequestParam("e") String note) {

        log.debug("Received request to add a patient note: patId: {}, note: {}", patId, note);

        NoteDto patientNote = new NoteDto(patId, note);
        noteService.createNote(patientNote);

        log.debug("Successfully added patient note: {}", patientNote);
        return ResponseEntity.ok("Patient note added");
    }
}
