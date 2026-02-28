package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;

public interface AccountBalanceUseCase {

	Money getAccountBalance(AccountBalanceQuery query);

	record AccountBalanceQuery(AccountId accountId) {
	}
}
