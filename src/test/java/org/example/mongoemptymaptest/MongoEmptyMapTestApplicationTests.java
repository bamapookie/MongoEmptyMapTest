package org.example.mongoemptymaptest;

import org.example.mongoemptymaptest.model.ChildDocument;
import org.example.mongoemptymaptest.model.ParentDocument;
import org.example.mongoemptymaptest.repository.ChildRepository;
import org.example.mongoemptymaptest.repository.ParentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
class MongoEmptyMapTestApplicationTests {

    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.25");

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ChildRepository childRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        // This empty method will fail if the context fails to load.
        // This test method ensures that the Spring application context loads successfully.
    }

    @ParameterizedTest(name = "raw json for {1}")
    @MethodSource("argumentsStream")
    void testRawJson(UUID id, String message) {
        System.out.printf("Raw JSON for %s.%n", message);
        mongoTemplate.find(
                new BasicQuery("{ _id: UUID(\"%s\") }".formatted(id)),
                String.class,
                "parent_documents"
        ).forEach(System.out::println);
        Assertions.assertTrue(true);
    }

    @ParameterizedTest(name = "findById for {1}")
    @MethodSource("argumentsStream")
    void testFindById(UUID id, String message) {
        Assertions.assertDoesNotThrow(() -> parentRepository.findById(id), "Unable to deserialize %s.".formatted(message));
    }

    @ParameterizedTest(name = "Map.values for {1}")
    @MethodSource("argumentsStream")
    void testMapValues(UUID id, String message) {
        parentRepository.findById(id).ifPresent(parent -> {
            Assertions.assertNotNull(parent.getDbRefChildren().values(), "Unable to get values collection map for %s.".formatted(message));
            Assertions.assertNotNull(parent.getLazyDbRefChildren().values(), "Unable to get values collection map for %s.".formatted(message));
            Assertions.assertNotNull(parent.getDocumentReferenceChildren().values(), "Unable to get values collection map for %s.".formatted(message));
            Assertions.assertNotNull(parent.getLazyDocumentReferenceChildren().values(), "Unable to get values collection map for %s.".formatted(message));
        });
    }

    private static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of(Fixtures.PARENT_WITH_CHILD.getId(), "parent with child"),
                Arguments.of(Fixtures.PARENT_WITH_NO_DBREF_CHILD.getId(), "parent with no @DBRef child"),
                Arguments.of(Fixtures.PARENT_WITH_NO_LAZY_DBREF_CHILD.getId(), "parent with no lazy @DBRef child"),
                Arguments.of(Fixtures.PARENT_WITH_NO_DOCUMENT_REFERENCE_CHILD.getId(), "parent with no @DocumentReference child"),
                Arguments.of(Fixtures.PARENT_WITH_NO_LAZY_DOCUMENT_REFERENCE_CHILD.getId(), "parent with no lazy @DocumentReference child")
        );
    }

    @BeforeEach
    void setUp() {
        childRepository.save(Fixtures.CHILD);
        parentRepository.save(Fixtures.PARENT_WITH_CHILD);
        parentRepository.save(Fixtures.PARENT_WITH_NO_DBREF_CHILD);
        parentRepository.save(Fixtures.PARENT_WITH_NO_LAZY_DBREF_CHILD);
        parentRepository.save(Fixtures.PARENT_WITH_NO_DOCUMENT_REFERENCE_CHILD);
        parentRepository.save(Fixtures.PARENT_WITH_NO_LAZY_DOCUMENT_REFERENCE_CHILD);
    }

    @AfterEach
    void tearDown() {
        childRepository.deleteAll();
        parentRepository.deleteAll();
    }

    static class Fixtures {
        public static final ChildDocument CHILD = ChildDocument.builder().build();
        public static final ParentDocument PARENT_WITH_CHILD = ParentDocument.builder()
                .dbRefChildren(Map.of("CHILD", CHILD))
                .lazyDbRefChildren(Map.of("CHILD", CHILD))
                .documentReferenceChildren(Map.of("CHILD", CHILD))
                .lazyDocumentReferenceChildren(Map.of("CHILD", CHILD))
                .build();
        public static final ParentDocument PARENT_WITH_NO_DBREF_CHILD = ParentDocument.builder()
                .lazyDbRefChildren(Map.of("CHILD", CHILD))
                .documentReferenceChildren(Map.of("CHILD", CHILD))
                .lazyDocumentReferenceChildren(Map.of("CHILD", CHILD))
                .build();
        public static final ParentDocument PARENT_WITH_NO_LAZY_DBREF_CHILD = ParentDocument.builder()
                .dbRefChildren(Map.of("CHILD", CHILD))
                .documentReferenceChildren(Map.of("CHILD", CHILD))
                .lazyDocumentReferenceChildren(Map.of("CHILD", CHILD))
                .build();
        public static final ParentDocument PARENT_WITH_NO_DOCUMENT_REFERENCE_CHILD = ParentDocument.builder()
                .dbRefChildren(Map.of("CHILD", CHILD))
                .lazyDbRefChildren(Map.of("CHILD", CHILD))
                .lazyDocumentReferenceChildren(Map.of("CHILD", CHILD))
                .build();
        public static final ParentDocument PARENT_WITH_NO_LAZY_DOCUMENT_REFERENCE_CHILD = ParentDocument.builder()
                .dbRefChildren(Map.of("CHILD", CHILD))
                .lazyDbRefChildren(Map.of("CHILD", CHILD))
                .documentReferenceChildren(Map.of("CHILD", CHILD))
                .build();
    }
}
