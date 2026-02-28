package buckpal.adapter.out.persistence;

import buckpal.application.domain.model.Account.AccountId;
import buckpal.common.IAccountLock;

import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements IAccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}

}
