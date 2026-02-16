package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.port.in.AccountDepositCommand;
import io.reflectoring.buckpal.application.port.in.AccountDepositUseCase;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import io.reflectoring.buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class AccountDepositService implements AccountDepositUseCase {

	private final LoadAccountPort loadAccountPort;
	private final UpdateAccountStatePort updateAccountStatePort;

	@Override
	public boolean deposit(AccountDepositCommand command) {
		Account account = loadAccountPort.loadAccount(command.accountId());
		// Use a system account as the source for external deposits
		AccountId externalSourceAccount = new AccountId(0L);
		boolean success = account.deposit(command.money(), externalSourceAccount);
		updateAccountStatePort.updateActivities(account);
		return success;
	}

}
