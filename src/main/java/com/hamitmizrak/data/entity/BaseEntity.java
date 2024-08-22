package com.hamitmizrak.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Getter
@Setter

// Entity Base
@MappedSuperclass
public class BaseEntity  implements Serializable {

    // Serileştirme
    private final static Long serialVersionUID=1L;

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Mysql, H2DB
    @Column(name = "id", unique = true, nullable = false, updatable = false,insertable = true)
    private Long id;

    // Date
    @Column(name = "created_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
