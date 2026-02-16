package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.AccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class AccountBalanceService implements AccountBalanceUseCase {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(GetAccountBalanceQuery query) {
		return loadAccountPort.loadAccount(query.accountId())
				.calculateBalance();
	}
}
