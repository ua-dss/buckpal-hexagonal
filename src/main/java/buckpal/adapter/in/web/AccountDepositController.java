package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.AccountBalanceUseCase;
import buckpal.application.port.in.AccountDepositCommand;
import buckpal.application.port.in.AccountDepositUseCase;
import buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountDepositController {

	private final AccountDepositUseCase accountDepositUseCase;
	private final AccountBalanceUseCase accountBalanceUseCase;

	@PostMapping(path = "/accounts/deposit")
	DepositResponse deposit(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountDepositCommand command = new AccountDepositCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = accountDepositUseCase.deposit(command);

			AccountBalanceUseCase.AccountBalanceQuery query =
					new AccountBalanceUseCase.AccountBalanceQuery(
							new AccountId(accountId));

			Long balance = accountBalanceUseCase.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new DepositResponse(success, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new DepositResponse(false, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record DepositResponse(boolean success, AccountInfo account, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}

}
