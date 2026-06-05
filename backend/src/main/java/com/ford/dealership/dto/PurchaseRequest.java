package com.ford.dealership.dto;

import com.ford.dealership.entity.Payment;
import com.ford.dealership.model.Cor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Payload enviado pelo front ao concluir uma compra.
 * O usuario eh derivado do token JWT (nao vem no corpo).
 */
@Data
public class PurchaseRequest {

    @NotNull
    private String carId;

    private Cor selectedColor;

    private Payment payment;
}
