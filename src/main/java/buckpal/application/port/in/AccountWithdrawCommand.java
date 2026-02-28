package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public record AccountWithdrawCommand(
		@NotNull AccountId accountId,
		@NotNull @PositiveMoney Money money

) {

	public AccountWithdrawCommand(AccountId accountId, Money money) {
		this.accountId = accountId;
		this.money = money;
		validate(this);
	}

}
