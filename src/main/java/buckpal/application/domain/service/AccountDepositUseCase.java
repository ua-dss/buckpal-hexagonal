package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.ForDepositAccount;
import buckpal.application.port.out.ForGettingAccount;
import buckpal.application.port.out.ForUpdatingAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@IUseCase
public class AccountDepositUseCase implements ForDepositAccount {

	private final ForGettingAccount loadAccountPort;
	private final ForUpdatingAccount updateAccountStatePort;

	@Override
	public boolean deposit(DepositCommand command) {
		Account account = loadAccountPort.loadAccount(command.accountId());
		// Use a system account as the source for external deposits
		AccountId externalSourceAccount = new AccountId(0L);
		boolean success = account.deposit(command.money(), externalSourceAccount);
		updateAccountStatePort.updateActivities(account);
		return success;
	}

}
