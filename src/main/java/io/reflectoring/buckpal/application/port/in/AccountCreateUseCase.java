package io.reflectoring.buckpal.application.port.in;

import io.reflectoring.buckpal.application.domain.model.Account.AccountId;

public interface AccountCreateUseCase {

	AccountId createAccount(AccountCreateCommand command);

}
