package buckpal.application.domain.usecase;

import buckpal.application.domain.model.Account;
import buckpal.application.port.in.ForListAccount;
import buckpal.application.port.out.ForGetAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@IUseCase
@Transactional
public class AccountListAllUseCase implements ForListAccount {

	private final ForGetAccount loadPort;

	@Override
	public List<Account> listAccounts() {
		return loadPort.getAllAccounts();
	}

}
