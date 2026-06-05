package com.ford.dealership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Especificacoes tecnicas de um carro. Persistido como JSON na entidade Car.
 * Os nomes seguem exatamente o contrato consumido pelo front (snake_case onde aplicavel).
 */
@Data
@NoArgsConstructor
public class Especificacoes {
    private String motor;
    private String potencia;
    private String torque;
    private String aceleracao;
    private String suspensao;
    @JsonProperty("velocidade_maxima")
    private String velocidadeMaxima;
    private String combustivel;
    private String tracao;
    @JsonProperty("aceleracao_0_100")
    private String aceleracao0100;
}
