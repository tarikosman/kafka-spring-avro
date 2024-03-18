package com.example.spring.kafka.demo.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.spring.kafka.demo.service.PersonDto;
import com.example.spring.kafka.demo.service.PersonService;

import lombok.RequiredArgsConstructor;

/**
 * Openapi generates the API and uses this delegator as effective
 * implementation. This has the advantage, that the class contains no API
 * specific annotations.
 */
@RequiredArgsConstructor
@Component
class PersonRestControllerDelegator implements PersonsApiDelegate {

    private final PersonService personService;

    @Override
    public ResponseEntity<PersonDto> add(PersonDto personDto) {
        return ResponseEntity.ok(personService.add(personDto));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @Override
    public ResponseEntity<PersonDto> getById(UUID id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @Override
    public ResponseEntity<PersonDto> update(UUID id, PersonDto personDto) {
        return ResponseEntity.ok(personService.update(id, personDto));
    }
}