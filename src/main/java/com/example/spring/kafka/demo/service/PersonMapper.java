package com.example.spring.kafka.demo.service;

import java.util.Collection;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.spring.kafka.demo.config.MapperSupport;
import com.example.spring.kafka.demo.config.MapstructMapperUtil;
import com.example.spring.kafka.demo.persistence.Address;
import com.example.spring.kafka.demo.persistence.Person;
import com.example.spring.kafka.demo.service.PersonEvent.Event;

/**
 * Provides persistence related mapping, including the creation of application
 * events.
 */
@Mapper
interface PersonMapper extends MapperSupport {
    // Base entity mapping
    @Mapping(target = "id", ignore = true)
    void toEntity(PersonDto dto, @MappingTarget Person entity);

    PersonDto toDto(Person entity);

    // Mapping for child address collections
    void mergeAddress(Address source, @MappingTarget Address target);

    Address toAddress(AddressDto source);

    /**
     * Merges a collection from the DTOs to the JPA entities collection, by adding
     * and removing entities in the target collection. This allows Hibernate to
     * correctly persist the parent entity and remove orphaned children.
     * 
     * @param sourceCollection source collection
     * @param targetCollection target collection
     */
    default void mergeAddresses(Collection<AddressDto> sourceCollection,
            @MappingTarget Collection<Address> targetCollection) {
        MapstructMapperUtil.merge(sourceCollection, targetCollection, this::toAddress, this::mergeAddress);
    }

    // Mapping to application events
    @Mapping(target = "event", constant = "CREATED")
    PersonEvent createEvent(PersonDto dto);

    @Mapping(target = "event", constant = "UPDATED")
    PersonEvent updateEvent(PersonDto dto);

    default PersonEvent deleteEvent(UUID id) {
        var event = new PersonEvent();
        event.setId(id);
        event.setEvent(Event.DELETED);
        return event;
    }
}
