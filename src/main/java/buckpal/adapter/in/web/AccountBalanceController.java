package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.port.in.ForBalanceAccount;
import buckpal.common.IWebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IWebAdapter
@RestController
@RequiredArgsConstructor
class AccountBalanceController {

	private final ForBalanceAccount balancePort;

	@GetMapping(path = "/accounts/balance")
	BalanceResponse getAccountBalance(@RequestParam Long accountId) {

		try {
			ForBalanceAccount.BalanceQuery query =
					new ForBalanceAccount.BalanceQuery(
							new AccountId(accountId));

			Long balance = balancePort.getBalance(query)
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
