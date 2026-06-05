package com.ford.dealership.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dados de pagamento de uma compra. Espelha o PaymentData do front.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    /** "pix", "card" ou "financing". */
    private String paymentMethod;
    private Double downPayment;
    private Integer installments;
}
