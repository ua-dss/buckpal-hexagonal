package io.reflectoring.buckpal.application.port.in;

import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountWithdrawCommand(
		@NotNull AccountId accountId,
		@NotNull Money money
) {

	public AccountWithdrawCommand(AccountId accountId, Money money) {
		this.accountId = accountId;
		this.money = money;
		validate(this);
	}

}
