package io.reflectoring.buckpal.application.port.out;

import java.util.List;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId);

	List<Account> getAllAccounts();
}
