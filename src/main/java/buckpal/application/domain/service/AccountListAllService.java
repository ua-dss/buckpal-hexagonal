package buckpal.application.domain.service;

import buckpal.application.domain.model.Account;
import buckpal.application.port.in.AccountListAllUseCase;
import buckpal.application.port.out.LoadAccountPort;
import buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AccountListAllService implements AccountListAllUseCase {

	private final LoadAccountPort loadAccountPort;

	@Override
	public List<Account> listAccounts() {
		return loadAccountPort.getAllAccounts();
	}

}
