package buckpal.common;

import buckpal.application.domain.model.Account;

public interface IAccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}
