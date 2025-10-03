package org.example.mongoemptymaptest.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document(collection = "child_documents")
public class ChildDocument {

    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();
}
