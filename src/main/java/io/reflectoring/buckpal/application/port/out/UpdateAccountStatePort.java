package io.reflectoring.buckpal.application.port.out;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

	AccountId createAccount(Account account);

}
