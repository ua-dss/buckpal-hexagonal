package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.port.in.AccountListAllUseCase;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.common.UseCase;
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
