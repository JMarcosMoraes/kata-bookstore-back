package com.kata.books.domain.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnoPublicacaoValidator implements ConstraintValidator<AnoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return false;

        try {
            int ano = Integer.parseInt(value);
            int anoAtual = java.time.Year.now().getValue();
            return ano <= anoAtual;
        } catch (NumberFormatException e) {
            return false; // não é número válido
        }
    }
}