package com.hamitmizrak.data.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2

// Entity
@Entity(name = "Customer")
@Table(name="customer")
// CustomerEntity
public class CustomerEntity extends BaseEntity  implements Serializable {

    // Serileştirme
    private final static Long serialVersionUID = 1L;

    // NAME
    @Column(name="customer_name",length=250, columnDefinition = "varchar(255) default 'müşteri adı girilmedi'")
    private String customerName;

    // SURNAME
    @Column(name="customer_surname")
    private String customerSurname;

    // E-mail
    @Column(name="customer_email")
    private String customerEmail;

    // Password
    @Column(name="customer_password")
    private String customerPassword;

} //end CustomerEntity
