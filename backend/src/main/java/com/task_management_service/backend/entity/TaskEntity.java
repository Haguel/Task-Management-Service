package com.task_management_service.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.task_management_service.backend.enumeration.TaskStatus;
import com.task_management_service.backend.utils.Converter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime untilDate;

    @Column(nullable = false)
    private TaskStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private Instant createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private UserEntity user;

    public void setUntilDate(String iso8601) {
        this.untilDate = Converter.convertToLocalDateTime(iso8601);
    }
}
