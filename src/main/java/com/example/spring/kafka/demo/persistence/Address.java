package com.example.spring.kafka.demo.persistence;

import com.example.spring.kafka.demo.common.JpaEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Address extends JpaEntity {
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

}
