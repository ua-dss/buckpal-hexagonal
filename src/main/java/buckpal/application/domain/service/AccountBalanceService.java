package buckpal.application.domain.service;

import buckpal.application.domain.model.Money;
import buckpal.application.port.in.AccountBalanceUseCase;
import buckpal.application.port.out.LoadAccountPort;
import buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class AccountBalanceService implements AccountBalanceUseCase {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(AccountBalanceQuery query) {
		return loadAccountPort.loadAccount(query.accountId())
				.calculateBalance();
	}
}
