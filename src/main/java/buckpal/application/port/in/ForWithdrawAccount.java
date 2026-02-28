package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.common.validation.IPositiveMoney;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public interface ForWithdrawAccount {

	boolean withdraw(WithdrawCommand command);

	public record WithdrawCommand(
			@NotNull AccountId accountId,
			@NotNull @IPositiveMoney Money money

	) {

		public WithdrawCommand(AccountId accountId, Money money) {
			this.accountId = accountId;
			this.money = money;
			validate(this);
		}

	}

}
