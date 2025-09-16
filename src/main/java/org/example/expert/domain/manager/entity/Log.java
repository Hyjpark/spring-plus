package org.example.expert.domain.manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.CreatedAtEntity;

@Getter
@Entity
@NoArgsConstructor
public class Log extends CreatedAtEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menagerId;

    public Log(Long menagerId) {
        this.menagerId = menagerId;
    }
}
