package buckpal.application.port.in;

import buckpal.application.domain.model.Account;

import java.util.List;

public interface AccountListAllUseCase {

	List<Account> listAccounts();

}
