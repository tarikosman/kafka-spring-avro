package com.example.spring.kafka.demo.service;

import java.util.Set;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.spring.kafka.demo.persistence.Address;
import com.example.spring.kafka.demo.persistence.Person;
import com.example.spring.kafka.demo.service.PersonEvent.Event;

@Mapper
interface PersonMapper {
    @Mapping(target = "id", ignore = true)
    void toEntity(PersonDto dto, @MappingTarget Person entity);

    PersonDto toDto(Person entity);

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

    Set<AddressDto> toDto(Set<Address> addresses);
}
