package com.example.spring.kafka.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.kafka.demo.persistence.Person;
import com.example.spring.kafka.demo.persistence.PersonRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Manages the persistence and publishes the person events.
 */
@Component
@RequiredArgsConstructor
public class PersonService {

    private final ApplicationEventPublisher publisher;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    public PersonDto getById(@NonNull UUID id) {
        var entity = personRepository.getReferenceById(id);
        return personMapper.toDto(entity);
    }

    @Transactional
    public PersonDto add(PersonDto dto) {
        var entity = new Person();
        personMapper.toEntity(dto, entity);
        entity = personRepository.saveAndFlush(entity);
        var result = personMapper.toDto(entity);

        var event = personMapper.createEvent(result);
        publisher.publishEvent(event);
        return result;
    }

    @Transactional
    public PersonDto update(@NonNull UUID id, @NonNull PersonDto dto) {
        var entity = personRepository.getReferenceById(id);
        personMapper.toEntity(dto, entity);
        entity = personRepository.saveAndFlush(entity);
        var result = personMapper.toDto(entity);

        var event = personMapper.updateEvent(result);
        publisher.publishEvent(event);

        return result;
    }

    @Transactional
    public void deleteById(@NonNull UUID id) {
        personRepository.deleteById(id);
        personRepository.flush();

        var event = personMapper.deleteEvent(id);
        publisher.publishEvent(event);
    }
}
