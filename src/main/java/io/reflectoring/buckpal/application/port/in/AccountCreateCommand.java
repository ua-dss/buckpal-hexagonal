package io.reflectoring.buckpal.application.port.in;

import io.reflectoring.buckpal.application.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountCreateCommand(
		@NotNull @PositiveMoney Money initialBalance
) {

    public AccountCreateCommand(Money initialBalance) {
        this.initialBalance = initialBalance;
        validate(this);
    }

}
