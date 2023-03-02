package com.mediscreen.note.service;

import com.mediscreen.note.model.DatabaseSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SequenceGeneratorServiceTest {


    @Mock
    private MongoOperations mongoOperations;
    @InjectMocks
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    @DisplayName("When Sequence Exists")
    void generateSequenceTest() {

        // Arrange
        String seqName = "invoice";
        DatabaseSequence counter = new DatabaseSequence();
        counter.setSeq(5);
        when(mongoOperations.findAndModify(any(), any(Update.class), any(), eq(DatabaseSequence.class)))
                .thenReturn(counter);

        // Act
        long result = sequenceGeneratorService.generateSequence(seqName);

        // Assert
        assertEquals(5, result);
    }


    @Test
    @DisplayName("When Sequence Does Not Exist")
    void generateSequenceTest2() {

        // Arrange
        String seqName = "invoice";
        when(mongoOperations.findAndModify(any(), any(Update.class), any(), eq(DatabaseSequence.class)))
                .thenReturn(null);

        // Act
        long result = sequenceGeneratorService.generateSequence(seqName);

        // Assert
        assertEquals(1, result);
    }
}
