package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.port.in.ForBalanceAccount;
import buckpal.application.port.in.ForSendMoneyAccount;
import buckpal.common.IWebAdapter;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IWebAdapter
@RestController
@RequiredArgsConstructor
class AccountSendMoneyController {

	private final ForSendMoneyAccount sendPort;
	private final ForBalanceAccount balancePort;

	@PostMapping(path = "/accounts/send")
	SendMoneyResponse sendMoney(
			@RequestParam("sourceAccountId") Long sourceAccountId,
			@RequestParam("targetAccountId") Long targetAccountId,
			@RequestParam("amount") Long amount) {

		try {
			ForSendMoneyAccount.SendMoneyCommand command = new ForSendMoneyAccount.SendMoneyCommand(
					new AccountId(sourceAccountId),
					new AccountId(targetAccountId),
					Money.of(amount));

			boolean success = sendPort.sendMoney(command);

			ForBalanceAccount.BalanceQuery sourceQuery = new ForBalanceAccount.BalanceQuery(
					new AccountId(sourceAccountId));

			ForBalanceAccount.BalanceQuery targetQuery = new ForBalanceAccount.BalanceQuery(
					new AccountId(targetAccountId));

			Long sourceBalance = balancePort.getBalance(sourceQuery)
					.getAmount()
					.longValue();

			Long targetBalance = balancePort.getBalance(targetQuery)
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
