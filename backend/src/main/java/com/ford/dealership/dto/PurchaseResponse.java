package com.ford.dealership.dto;

import java.time.Instant;

import com.ford.dealership.entity.Car;
import com.ford.dealership.entity.Payment;
import com.ford.dealership.entity.Purchase;
import com.ford.dealership.model.Cor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Compra devolvida ao front com user e car aninhados (como o dashboard/propostas esperam).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {
    private String id;
    private UserDTO user;
    private Car car;
    private Cor selectedColor;
    private Payment payment;
    private Instant purchaseDate;
    private String status;

    public static PurchaseResponse from(Purchase p) {
        return PurchaseResponse.builder()
                .id(p.getId())
                .user(p.getUser() != null ? UserDTO.from(p.getUser()) : null)
                .car(p.getCar())
                .selectedColor(p.getSelectedColor())
                .payment(p.getPayment())
                .purchaseDate(p.getPurchaseDate())
                .status(p.getStatus())
                .build();
    }
}
