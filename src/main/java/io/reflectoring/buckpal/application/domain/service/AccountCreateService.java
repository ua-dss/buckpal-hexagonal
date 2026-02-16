package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.ActivityWindow;
import io.reflectoring.buckpal.application.port.in.AccountCreateCommand;
import io.reflectoring.buckpal.application.port.in.AccountCreateUseCase;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import io.reflectoring.buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AccountCreateService implements AccountCreateUseCase {

	private final UpdateAccountStatePort updateAccountStatePort;

	@Override
	public AccountId createAccount(AccountCreateCommand command) {
		Account newAccount = Account.withoutId(
				command.initialBalance(),
				new ActivityWindow());

		return updateAccountStatePort.createAccount(newAccount);
	}

}
