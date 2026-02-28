package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.ForCreateAccount;
import buckpal.common.IWebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IWebAdapter
@RestController
@RequiredArgsConstructor
class AccountCreateController {

	private final ForCreateAccount createPort;

	@PostMapping(path = "/accounts/create")
	CreateResponse createAccount(@RequestParam Long initialBalance) {

		try {
			ForCreateAccount.CreateCommand command = new ForCreateAccount.CreateCommand(
					Money.of(initialBalance));

			AccountId accountId = createPort.createAccount(command);
			return new CreateResponse(true, new AccountInfo(accountId.getValue(), initialBalance), null);
		} catch (Exception e) {
			return new CreateResponse(false, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record CreateResponse(boolean success, AccountInfo account, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}

}
