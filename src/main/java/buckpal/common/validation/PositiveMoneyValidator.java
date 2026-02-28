package buckpal.common.validation;

import buckpal.application.domain.model.Money;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveMoneyValidator implements ConstraintValidator<IPositiveMoney, Money> {

    @Override
    public boolean isValid(Money value, ConstraintValidatorContext context) {
        return value.isPositive();
    }
}
