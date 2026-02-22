package io.reflectoring.buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.port.in.AccountBalanceUseCase;
import io.reflectoring.buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountBalanceController {

	private final AccountBalanceUseCase getAccountBalanceUseCase;

	@GetMapping(path = "/accounts/balance")
	BalanceResponse getAccountBalance(@RequestParam("accountId") Long accountId) {

		try {
			AccountBalanceUseCase.AccountBalanceQuery query =
					new AccountBalanceUseCase.AccountBalanceQuery(
							new AccountId(accountId));

			Long balance = getAccountBalanceUseCase.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new BalanceResponse(true, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new BalanceResponse(false, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record BalanceResponse(boolean success, AccountInfo account, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}
}
