package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.port.in.AccountSendMoneyCommand;
import io.reflectoring.buckpal.application.port.in.AccountSendMoneyUseCase;
import io.reflectoring.buckpal.application.port.out.AccountLock;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import io.reflectoring.buckpal.common.UseCase;
import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AccountSendMoneyService implements AccountSendMoneyUseCase {

	private final LoadAccountPort loadAccountPort;
	private final AccountLock accountLock;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean sendMoney(AccountSendMoneyCommand command) {

		checkThreshold(command);

		Account sourceAccount = loadAccountPort.loadAccount(
				command.sourceAccountId());

		Account targetAccount = loadAccountPort.loadAccount(
				command.targetAccountId());

		AccountId sourceAccountId = sourceAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.money(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(AccountSendMoneyCommand command) {
		if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
		}
	}

}




