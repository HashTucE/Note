package com.mediscreen.note.repository;

import com.mediscreen.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {
}
