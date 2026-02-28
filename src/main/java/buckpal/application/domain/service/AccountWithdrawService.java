package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.AccountWithdrawCommand;
import buckpal.application.port.in.AccountWithdrawUseCase;
import buckpal.application.port.out.LoadAccountPort;
import buckpal.application.port.out.UpdateAccountStatePort;
import buckpal.common.UseCase;
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
