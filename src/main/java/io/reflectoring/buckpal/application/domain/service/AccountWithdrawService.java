package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.port.in.AccountWithdrawCommand;
import io.reflectoring.buckpal.application.port.in.AccountWithdrawUseCase;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import io.reflectoring.buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class AccountWithdrawService implements AccountWithdrawUseCase {

	private final LoadAccountPort loadAccountPort;
	private final UpdateAccountStatePort updateAccountStatePort;

	@Override
	public boolean withdraw(AccountWithdrawCommand command) {
		Account account = loadAccountPort.loadAccount(command.accountId());
		// Use a system account as the target for external withdrawals
		AccountId externalTargetAccount = new AccountId(0L);
		boolean success = account.withdraw(command.money(), externalTargetAccount);
		updateAccountStatePort.updateActivities(account);
		return success;
	}

}
