package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.ForWithdrawAccount;
import buckpal.application.port.out.ForGettingAccount;
import buckpal.application.port.out.ForUpdatingAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@IUseCase
public class AccountWithdrawUseCase implements ForWithdrawAccount {

	private final ForGettingAccount loadAccountPort;
	private final ForUpdatingAccount updateAccountStatePort;

	@Override
	public boolean withdraw(WithdrawCommand command) {
		Account account = loadAccountPort.loadAccount(command.accountId());
		// Use a system account as the target for external withdrawals
		AccountId externalTargetAccount = new AccountId(0L);
		boolean success = account.withdraw(command.money(), externalTargetAccount);
		updateAccountStatePort.updateActivities(account);
		return success;
	}

}
