package buckpal.application.port.out;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

	AccountId createAccount(Account account);

}
