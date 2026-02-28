package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.common.validation.IPositiveMoney;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public interface ForSendMoneyAccount {

	boolean sendMoney(SendMoneyCommand command);

	public record SendMoneyCommand(
			@NotNull AccountId sourceAccountId,
			@NotNull AccountId targetAccountId,
			@NotNull @IPositiveMoney Money money) {

		public SendMoneyCommand(
				AccountId sourceAccountId,
				AccountId targetAccountId,
				Money money) {
			this.sourceAccountId = sourceAccountId;
			this.targetAccountId = targetAccountId;
			this.money = money;
			validate(this);
		}

	}

}
