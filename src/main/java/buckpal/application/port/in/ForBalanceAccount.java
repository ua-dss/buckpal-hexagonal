package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;

public interface ForBalanceAccount {

	Money getBalance(BalanceQuery query);

	record BalanceQuery(AccountId accountId) {
	}
}
