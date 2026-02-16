package io.reflectoring.buckpal.application.port.in;

import io.reflectoring.buckpal.application.domain.model.Account;

import java.util.List;

public interface AccountListAllUseCase {

	List<Account> listAccounts();

}
