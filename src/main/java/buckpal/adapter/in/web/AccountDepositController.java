package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.ForBalanceAccount;
import buckpal.application.port.in.ForDepositAccount;
import buckpal.common.IWebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IWebAdapter
@RestController
@RequiredArgsConstructor
class AccountDepositController {

	private final ForDepositAccount depositPort;
	private final ForBalanceAccount balancePort;

	@PostMapping(path = "/accounts/deposit")
	DepositResponse deposit(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			ForDepositAccount.DepositCommand command = new ForDepositAccount.DepositCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = depositPort.deposit(command);

			ForBalanceAccount.BalanceQuery query =
					new ForBalanceAccount.BalanceQuery(
							new AccountId(accountId));

			Long balance = balancePort.getBalance(query)
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
