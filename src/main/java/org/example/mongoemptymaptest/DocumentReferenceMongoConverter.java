package org.example.mongoemptymaptest;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

public class DocumentReferenceMongoConverter extends MappingMongoConverter {
    public DocumentReferenceMongoConverter(
            DbRefResolver dbRefResolver,
            MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext
    ) {
        super(dbRefResolver, mappingContext);
    }


}
