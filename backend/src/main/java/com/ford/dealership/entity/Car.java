package com.ford.dealership.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.dealership.converter.CoresListConverter;
import com.ford.dealership.converter.EquipamentosConverter;
import com.ford.dealership.converter.EspecificacoesConverter;
import com.ford.dealership.converter.StringListConverter;
import com.ford.dealership.model.Cor;
import com.ford.dealership.model.Equipamentos;
import com.ford.dealership.model.Especificacoes;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    private String modelo;

    private Integer ano;

    private Double preco;

    private Integer estoque;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String descricao;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String historia;

    private Boolean destaque;

    /** "SUV", "Esportivo" ou "Picape". */
    private String categoria;

    /** "Gasolina", "Eletrico", "Hibrido", "Diesel" ou "Etanol". */
    private String combustiveis;

    @JsonProperty("imagem_principal")
    private String imagemPrincipal;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = CoresListConverter.class)
    private List<Cor> cores;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = EspecificacoesConverter.class)
    private Especificacoes especificacoes;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = EquipamentosConverter.class)
    private Equipamentos equipamentos;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = StringListConverter.class)
    private List<String> galeria;

    private Instant createdAt;

    private Instant updatedAt;
}
