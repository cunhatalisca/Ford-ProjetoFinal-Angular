package com.ford.dealership.converter;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.persistence.Converter;

@Converter
public class StringListConverter extends JsonAttributeConverter<List<String>> {
    @Override
    protected TypeReference<List<String>> typeRef() {
        return new TypeReference<>() {
        };
    }
}
