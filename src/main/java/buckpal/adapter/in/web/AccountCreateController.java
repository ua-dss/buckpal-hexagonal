package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.AccountCreateCommand;
import buckpal.application.port.in.AccountCreateUseCase;
import buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountCreateController {

	private final AccountCreateUseCase createAccountUseCase;

	@PostMapping(path = "/accounts/create")
	CreateResponse createAccount(@RequestParam("initialBalance") Long initialBalance) {

		try {
			AccountCreateCommand command = new AccountCreateCommand(
					Money.of(initialBalance));

			AccountId accountId = createAccountUseCase.createAccount(command);
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
