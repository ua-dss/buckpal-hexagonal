package buckpal.application.port.in;

import buckpal.application.domain.model.Account.AccountId;

public interface AccountCreateUseCase {

	AccountId createAccount(AccountCreateCommand command);

}
