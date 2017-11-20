package cz.kucharo2.rest.validator;

import cz.kucharo2.rest.model.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class FormValidatorUtil {

    public static <T extends Object> List<FieldError> validate(T model) {
        Set<ConstraintViolation<T>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(model);
        List<FieldError> validationErrors = new ArrayList<>();
        errors.forEach(constraintViolation -> {
            validationErrors.add(new FieldError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        });
        return validationErrors;
    }

}
