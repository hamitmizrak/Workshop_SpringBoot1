package com.hamitmizrak.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2

// DTO (Validation)
public class CustomerDto implements Serializable {

    // Serileştirme
    private final static Long serialVersionUID=1L;

    // ID
    private Long id;

    // NAME
    // @NotEmpty(message = "Müşteri adını boş geçemezsiniz")
    @NotEmpty(message = "{customer.name.validation.constraints.NotNull.message}")
    private String customerName;

    // SURNAME
    @NotEmpty(message = "{customer.surname.validation.constraints.NotNull.message}")
    private String customerSurname;

    // E-mail
    @NotEmpty(message = "{customer.email.validation.constraints.NotNull.message}")
    @Size(min = 7,max = 20, message = "{customer.email.validation.regex.constraints.NotNull.message}")
    @Email
    private String customerEmail;

    // Password
    @NotEmpty(message = "{customer.password.validation.constraints.NotNull.message}")
    @Size(min = 7,max = 20, message = "{customer.password.pattern.validation.constraints.NotNull.message}")
    //@Pattern(regexp = "",message = "{}")
    private String customerPassword;

    // Date
    private Date createdDate;
} //end CustomerDto


