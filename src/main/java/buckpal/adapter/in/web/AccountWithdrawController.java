package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.ForBalanceAccount;
import buckpal.application.port.in.ForWithdrawAccount;
import buckpal.common.IWebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IWebAdapter
@RestController
@RequiredArgsConstructor
class AccountWithdrawController {

	private final ForWithdrawAccount withdrawPort;
	private final ForBalanceAccount balancePort;

	@PostMapping(path = "/accounts/withdraw")
	WithdrawResponse withdraw(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			ForWithdrawAccount.WithdrawCommand command = new ForWithdrawAccount.WithdrawCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = withdrawPort.withdraw(command);

			ForBalanceAccount.BalanceQuery query =
					new ForBalanceAccount.BalanceQuery(
							new AccountId(accountId));

			Long balance = balancePort.getBalance(query)
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
