package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.common.validation.IPositiveMoney;

import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public interface ForDepositAccount {

	boolean deposit(DepositCommand command);

	public record DepositCommand(
			@NotNull AccountId accountId,
			@NotNull @IPositiveMoney Money money

	) {

		public DepositCommand(AccountId accountId, Money money) {
			this.accountId = accountId;
			this.money = money;
			validate(this);
		}

	}
}
