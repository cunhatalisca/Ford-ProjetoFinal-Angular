package com.ford.dealership.converter;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ford.dealership.model.Cor;

import jakarta.persistence.Converter;

@Converter
public class CoresListConverter extends JsonAttributeConverter<List<Cor>> {
    @Override
    protected TypeReference<List<Cor>> typeRef() {
        return new TypeReference<>() {
        };
    }
}
