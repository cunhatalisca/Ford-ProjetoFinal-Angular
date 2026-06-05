package com.ford.dealership.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ford.dealership.model.Especificacoes;

import jakarta.persistence.Converter;

@Converter
public class EspecificacoesConverter extends JsonAttributeConverter<Especificacoes> {
    @Override
    protected TypeReference<Especificacoes> typeRef() {
        return new TypeReference<>() {
        };
    }
}
