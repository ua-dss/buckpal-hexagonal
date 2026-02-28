package buckpal.application.domain.usecase;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.ForDepositAccount;
import buckpal.application.port.out.ForGetAccount;
import buckpal.application.port.out.ForUpdateAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@IUseCase
public class AccountDepositUseCase implements ForDepositAccount {

	private final ForGetAccount loadPort;
	private final ForUpdateAccount updatePort;

	@Override
	public boolean deposit(DepositCommand command) {
		Account account = loadPort.loadAccount(command.accountId());
		// Use a system account as the source for external deposits
		AccountId externalSourceAccount = new AccountId(0L);
		boolean success = account.deposit(command.money(), externalSourceAccount);
		updatePort.updateActivities(account);
		return success;
	}

}
