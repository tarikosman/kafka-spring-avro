package com.example.spring.kafka.demo.config;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Provides common used functionality for mapping DTOs and JPA Entities.
 */
public interface MapstructMapperUtil {
    /**
     * Merges a collection from the DTOs to the JPA entities collection, by adding
     * and removing entities in the target collection. This allows Hibernate to
     * correctly persist the parent entity and remove orphaned children.
     * 
     * @param <S>              Source type (usually DTO)
     * @param <T>              Target type (usually JPA entity)
     * @param sourceCollection source collection
     * @param targetCollection target collection
     * @param mapper           mapping function to create a target entity from a
     *                         source dto
     * @param merger           mapping function, to merge the values in to the
     *                         target entity
     */
    public static <S, T> void merge(Collection<S> sourceCollection, Collection<T> targetCollection,
            Function<S, T> mapper, BiConsumer<T, T> merger) {
        List<T> mappedSourceCollection = sourceCollection.stream().map(mapper::apply).toList();
        targetCollection.removeIf(t -> !mappedSourceCollection.contains(t));
        mappedSourceCollection.stream().forEach(s -> {
            long count = targetCollection.stream()
                    .filter(t -> {
                        if (s.equals(t)) {
                            merger.accept(s, t);
                            return true;
                        }
                        return false;
                    }).count();
            // if no match was found, add the entity to the target collection
            if (count == 0) {
                targetCollection.add(s);
            }
        });
    }
}
