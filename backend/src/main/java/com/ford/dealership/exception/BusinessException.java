package com.ford.dealership.exception;

/**
 * Erro de regra de negocio que resulta em HTTP 400/409 (ex.: email duplicado, sem estoque).
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
