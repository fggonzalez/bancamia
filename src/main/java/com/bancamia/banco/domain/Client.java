package com.bancamia.banco.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Client {
    private Long id;

    @NotBlank(message = "El tipo de identificación es obligatorio")
    @Pattern(regexp = "^(CC|TI|CE)$", message = "El tipo de identificación debe ser CC, TI, o CE")
    private String identificationType;
    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(max = 20, message = "El número de identificación no puede tener más de 20 caracteres")
    private String identificationNumber;
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre debe contener solo letras")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El apellido debe contener solo letras")
    private String lastName;
    @NotBlank(message = "La fecha de creación es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
    private String creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}