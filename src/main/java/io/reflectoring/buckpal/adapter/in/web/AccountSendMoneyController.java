package io.reflectoring.buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.reflectoring.buckpal.application.port.in.AccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.in.AccountSendMoneyUseCase;
import io.reflectoring.buckpal.application.port.in.AccountSendMoneyCommand;
import io.reflectoring.buckpal.common.WebAdapter;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountSendMoneyController {

	private final AccountSendMoneyUseCase sendMoneyUseCase;
	private final AccountBalanceUseCase accountBalanceUseCase;

	@PostMapping(path = "/accounts/send")
	SendMoneyResponse sendMoney(
			@RequestParam("sourceAccountId") Long sourceAccountId,
			@RequestParam("targetAccountId") Long targetAccountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountSendMoneyCommand command = new AccountSendMoneyCommand(
					new AccountId(sourceAccountId),
					new AccountId(targetAccountId),
					Money.of(amount));

			boolean success = sendMoneyUseCase.sendMoney(command);

			AccountBalanceUseCase.GetAccountBalanceQuery sourceQuery =
					new AccountBalanceUseCase.GetAccountBalanceQuery(
							new AccountId(sourceAccountId));

			AccountBalanceUseCase.GetAccountBalanceQuery targetQuery =
					new AccountBalanceUseCase.GetAccountBalanceQuery(
							new AccountId(targetAccountId));

			Long sourceBalance = accountBalanceUseCase.getAccountBalance(sourceQuery)
					.getAmount()
					.longValue();

			Long targetBalance = accountBalanceUseCase.getAccountBalance(targetQuery)
					.getAmount()
					.longValue();

			return new SendMoneyResponse(
					success,
					new AccountInfo(sourceAccountId, sourceBalance),
					new AccountInfo(targetAccountId, targetBalance),
					null);
		} catch (Exception e) {
			return new SendMoneyResponse(false, null, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record SendMoneyResponse(boolean success, AccountInfo source, AccountInfo target, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}

}
