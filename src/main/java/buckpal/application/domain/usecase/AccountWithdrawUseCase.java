package buckpal.application.domain.usecase;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.ForWithdrawAccount;
import buckpal.application.port.out.ForGetAccount;
import buckpal.application.port.out.ForUpdateAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@IUseCase
public class AccountWithdrawUseCase implements ForWithdrawAccount {

	private final ForGetAccount loadPort;
	private final ForUpdateAccount updatePort;

	@Override
	public boolean withdraw(WithdrawCommand command) {
		Account account = loadPort.loadAccount(command.accountId());
		// Use a system account as the target for external withdrawals
		AccountId externalTargetAccount = new AccountId(0L);
		boolean success = account.withdraw(command.money(), externalTargetAccount);
		updatePort.updateActivities(account);
		return success;
	}

}
