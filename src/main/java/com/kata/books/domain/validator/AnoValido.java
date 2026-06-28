package com.kata.books.domain.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AnoPublicacaoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoValido {
    String message() default "O ano de publicação não pode ser no futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}