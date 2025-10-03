package org.example.mongoemptymaptest.repository;

import org.example.mongoemptymaptest.model.ChildDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChildRepository extends MongoRepository<ChildDocument, UUID> {
}
