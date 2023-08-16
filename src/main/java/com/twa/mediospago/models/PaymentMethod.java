package com.twa.mediospago.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "PaymentMethod")
@Getter
@Setter
@Table(name="paymentMethods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Name no puede estar vacío")
    @Size(max = 50, message = "Name no puede superar los 50 caracteres")
    private String name;
}
