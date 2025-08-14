package com.fbn.case_study.entity;

import com.fbn.case_study.utils.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity_logs")
public class ActivityLogs extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(500)", nullable = false, updatable = false)
    private String activity;

    @Column(updatable = false, nullable = false)
    private String initiatedBy;

    @Column(columnDefinition = "VARCHAR(5000)", updatable = false)
    @Convert(converter = AttributeEncryptor.class)
    private String payload;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false )
    private String responseStatus;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false, updatable = false )
    private String route;

}
