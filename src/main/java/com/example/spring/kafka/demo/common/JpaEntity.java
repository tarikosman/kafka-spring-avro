package com.example.spring.kafka.demo.common;

import java.util.UUID;

import org.hibernate.Hibernate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Base class for all JPA entities.
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Override
    public final boolean equals(Object thatObj) {
        if (this == thatObj) {
            return true;
        }
        if (thatObj == null) {
            return false;
        }
        Class<?> thisClass = Hibernate.unproxy(this).getClass();
        Class<?> thatClass = Hibernate.unproxy(thatObj).getClass();
        if (!(thisClass.isInstance(thatClass))) {
            return false;
        }

        JpaEntity that = (JpaEntity) thatObj;

        return this.id != null && this.id.equals(that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
