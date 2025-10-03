# Mongo Empty Map Bug

Spring Data MongoDB issue: https://github.com/spring-projects/spring-data-mongodb/issues/5065
If a map parameter of a document is annotated with Lombok's @NonNull and @DocumentReference and is
persisted with an empty map, that map will fail to deserialize in one of 2 ways:
1. If the `@DocumentReference` is eager, the object creation will fail when a null value is passed
   in the constructor.
2. If the `@DocumentReference` is lazy, the proxy will fail to resolve the map properly.  For
   instance, if the getter for the map is called, and the map's `values()` method is called, it
   will return `null`.

## Running the tests

Tests have been created to illustrate the problem.  Run `gradle test`.
