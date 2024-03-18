package com.example.spring.kafka.demo.persistence;

import java.util.HashSet;
import java.util.Set;

import com.example.spring.kafka.demo.common.JpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Person extends JpaEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_person")
    private Set<Address> addresses = new HashSet<>();
}