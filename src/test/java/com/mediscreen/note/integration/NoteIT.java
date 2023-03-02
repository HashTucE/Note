package com.mediscreen.note.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.note.controller.NoteController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoteIT {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private NoteController noteController;


    @BeforeAll
    public void setUp() {
        mongoTemplate.getDb().drop();
        NoteDto firstNote = new NoteDto(2, "firstNote");
        NoteDto secondNote = new NoteDto(2, "secondNote");
        noteController.validateNote(firstNote);
        noteController.validateNote(secondNote);
    }


    @Test
    @Order(1)
    @DisplayName("Should return note when name exist")
    void findNoteByIdTest() throws Exception {

        int id = 1;
        mockMvc.perform(get("/api/note/find/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientId").value(2))
                .andExpect(jsonPath("$.note").value("firstNote"));
    }


    @Test
    @Order(2)
    @DisplayName("Should return list of notes when exist")
    void getAllNotesTest() throws Exception {

        mockMvc.perform(get("/api/note/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].patientId").value(2))
                .andExpect(jsonPath("$.[0].note").value("firstNote"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].patientId").value(2))
                .andExpect(jsonPath("$.[1].note").value("secondNote"));
    }


    @Test
    @Order(3)
    @DisplayName("Should return note when id exist")
    void getNotesByPatientIdTest() throws Exception {

        int patientId = 2;
        mockMvc.perform(get("/api/note/list/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].patientId").value(2))
                .andExpect(jsonPath("$.[0].note").value("firstNote"))
                .andExpect(jsonPath("$.[1].patientId").value(2))
                .andExpect(jsonPath("$.[1].note").value("secondNote"));
    }


    @Test
    @Order(4)
    @DisplayName("Should return note when valid data")
    public void validateNoteTest() throws Exception {

        NoteDto noteDto = new NoteDto(1, "thirdNote");

        mockMvc.perform(post("/api/note/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.note").value("thirdNote"));
    }


    @Test
    @Order(5)
    @DisplayName("Should update note when valid data")
    public void updateNoteTest() throws Exception {

        NoteDto noteDto = new NoteDto(6, "fourthNote");

        mockMvc.perform(post("/api/note/update/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.patientId").value(6))
                .andExpect(jsonPath("$.note").value("fourthNote"));
    }


    @Test
    @Order(6)
    @DisplayName("Should delete note when id exist")
    public void deleteNoteTest() throws Exception {

        mockMvc.perform(post("/api/note/delete/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

