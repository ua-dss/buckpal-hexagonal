package buckpal.application.domain.service;

import buckpal.application.domain.model.Money;
import buckpal.application.port.in.ForBalanceAccount;
import buckpal.application.port.out.ForGetAccount;
import buckpal.common.IUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@IUseCase
public class AccountBalanceUseCase implements ForBalanceAccount {

	private final ForGetAccount loadPort;

	@Override
	public Money getBalance(BalanceQuery query) {
		return loadPort.loadAccount(query.accountId())
				.calculateBalance();
	}
}
