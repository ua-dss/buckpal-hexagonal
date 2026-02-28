package buckpal.application.port.in;

import buckpal.application.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public record AccountCreateCommand(
		@NotNull @PositiveMoney Money initialBalance
) {

    public AccountCreateCommand(Money initialBalance) {
        this.initialBalance = initialBalance;
        validate(this);
    }

}
