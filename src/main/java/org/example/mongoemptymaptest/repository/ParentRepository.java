package org.example.mongoemptymaptest.repository;

import org.example.mongoemptymaptest.model.ParentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParentRepository extends MongoRepository<ParentDocument, UUID> {
}
