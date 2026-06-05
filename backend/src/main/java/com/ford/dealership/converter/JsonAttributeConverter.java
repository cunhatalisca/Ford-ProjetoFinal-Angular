package com.ford.dealership.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

/**
 * Base para converters que serializam objetos/colecoes como JSON em uma coluna de texto.
 * Garante uma API REST limpa (objetos reais) mantendo a persistencia simples no H2.
 */
public abstract class JsonAttributeConverter<T> implements AttributeConverter<T, String> {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    protected abstract TypeReference<T> typeRef();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao serializar para JSON", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        try {
            return MAPPER.readValue(dbData, typeRef());
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao desserializar JSON", e);
        }
    }
}
