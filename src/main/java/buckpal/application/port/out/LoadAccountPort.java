package buckpal.application.port.out;

import java.util.List;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId);

	List<Account> getAllAccounts();
}
