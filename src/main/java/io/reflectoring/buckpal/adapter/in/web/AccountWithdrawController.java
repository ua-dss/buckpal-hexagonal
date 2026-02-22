package io.reflectoring.buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.AccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.in.AccountWithdrawCommand;
import io.reflectoring.buckpal.application.port.in.AccountWithdrawUseCase;
import io.reflectoring.buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountWithdrawController {

	private final AccountWithdrawUseCase accountWithdrawUseCase;
	private final AccountBalanceUseCase accountBalanceUseCase;

	@PostMapping(path = "/accounts/withdraw")
	WithdrawResponse withdraw(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountWithdrawCommand command = new AccountWithdrawCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = accountWithdrawUseCase.withdraw(command);

			AccountBalanceUseCase.AccountBalanceQuery query =
					new AccountBalanceUseCase.AccountBalanceQuery(
							new AccountId(accountId));

			Long balance = accountBalanceUseCase.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new WithdrawResponse(success, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new WithdrawResponse(false, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record WithdrawResponse(boolean success, AccountInfo account, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}

}
