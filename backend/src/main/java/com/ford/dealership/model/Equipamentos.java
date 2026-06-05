package com.ford.dealership.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Equipamentos de um carro, agrupados por categoria. Persistido como JSON na entidade Car.
 */
@Data
@NoArgsConstructor
public class Equipamentos {
    private List<String> seguranca;
    private List<String> tecnologia;
    private List<String> interior;
}
