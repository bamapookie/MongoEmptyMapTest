# Mongo Empty Map Bug

Spring Data MongoDB issue: https://github.com/spring-projects/spring-data-mongodb/issues/5065

If a map parameter of a document is annotated with Lombok's @NonNull and @DocumentReference and is
persisted with an empty map, that map will fail to deserialize in one of 2 ways:
1. If the `@DocumentReference` is eager, the object creation will fail when a null value is passed
   in the constructor.
2. If the `@DocumentReference` is lazy, the proxy will fail to resolve the map properly.  For
   instance, if the getter for the map is called, and the map's `values()` method is called, it
   will return `null`.

## Requirements

- Java 21 - Tested with Java 17 as well.
- Docker - To run Junit5 tests in TestContainers

## Running the tests

Tests have been created to illustrate the problem.  Run `gradle test`.

Due to the bug, there are two failing tests:
1. findById for parent with no @DocumentReference child

   Fails due to the ParentDocument constructor disallowing a null for the `@NonNull` `documentReferenceChildren` map.
2. Map.values for parent with no lazy @DocumentReference child

   Fails due to `Map.values()` method returning null when it should be populated.

Note that the equivalent tests for `@DBRef` work without problem.