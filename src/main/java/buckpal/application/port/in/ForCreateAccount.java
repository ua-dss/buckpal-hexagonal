package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.common.validation.IPositiveMoney;
import jakarta.validation.constraints.NotNull;
import static buckpal.common.validation.Validation.validate;

public interface ForCreateAccount {

	AccountId createAccount(CreateCommand command);

	public record CreateCommand(
			@NotNull @IPositiveMoney Money initialBalance) {

		public CreateCommand(Money initialBalance) {
			this.initialBalance = initialBalance;
			validate(this);
		}

	}
}
