package com.ford.dealership.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ford.dealership.model.Equipamentos;

import jakarta.persistence.Converter;

@Converter
public class EquipamentosConverter extends JsonAttributeConverter<Equipamentos> {
    @Override
    protected TypeReference<Equipamentos> typeRef() {
        return new TypeReference<>() {
        };
    }
}
