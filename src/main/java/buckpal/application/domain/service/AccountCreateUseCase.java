package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.ActivityWindow;
import buckpal.application.port.in.ForCreateAccount;
import buckpal.application.port.out.ForUpdateAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@IUseCase
@Transactional
public class AccountCreateUseCase implements ForCreateAccount {

	private final ForUpdateAccount updateAccountStatePort;

	@Override
	public AccountId createAccount(CreateCommand command) {
		Account newAccount = Account.withoutId(
				command.initialBalance(),
				new ActivityWindow());

		return updateAccountStatePort.createAccount(newAccount);
	}

}
