package com.ford.dealership.entity;

import java.time.Instant;

import com.ford.dealership.converter.CorConverter;
import com.ford.dealership.model.Cor;

import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    @Lob
    @Convert(converter = CorConverter.class)
    private Cor selectedColor;

    @Embedded
    private Payment payment;

    private Instant purchaseDate;

    /** "pendente", "aprovado" ou "rejeitado". */
    private String status;
}
