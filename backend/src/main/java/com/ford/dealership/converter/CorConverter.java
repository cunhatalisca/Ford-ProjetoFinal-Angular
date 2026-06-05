package com.ford.dealership.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ford.dealership.model.Cor;

import jakarta.persistence.Converter;

@Converter
public class CorConverter extends JsonAttributeConverter<Cor> {
    @Override
    protected TypeReference<Cor> typeRef() {
        return new TypeReference<>() {
        };
    }
}
