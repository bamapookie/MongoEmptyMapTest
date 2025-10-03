package org.example.mongoemptymaptest.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Document(collection = "parent_documents")
public class ParentDocument {

    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();

    @NonNull
    @Builder.Default
    @DBRef
    Map<String, ChildDocument> dbRefChildren = new HashMap<>();

    @NonNull
    @Builder.Default
    @DBRef(lazy = true)
    Map<String, ChildDocument> lazyDbRefChildren = new HashMap<>();

    @NonNull
    @Builder.Default
    @DocumentReference
    Map<String, ChildDocument> documentReferenceChildren = new HashMap<>();

    @NonNull
    @Builder.Default
    @DocumentReference(lazy = true)
    Map<String, ChildDocument> lazyDocumentReferenceChildren = new HashMap<>();
}
