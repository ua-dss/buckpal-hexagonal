package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.ActivityWindow;
import buckpal.application.port.in.AccountCreateCommand;
import buckpal.application.port.in.AccountCreateUseCase;
import buckpal.application.port.out.UpdateAccountStatePort;
import buckpal.common.UseCase;
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
