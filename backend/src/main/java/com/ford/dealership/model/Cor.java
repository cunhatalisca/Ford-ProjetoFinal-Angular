package com.ford.dealership.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cor disponivel para um carro. Mapeado como JSON dentro da entidade Car.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cor {
    private String nome;
    private Double precoAdicional;
    private String codigoCor;
    private String imagem;
}
