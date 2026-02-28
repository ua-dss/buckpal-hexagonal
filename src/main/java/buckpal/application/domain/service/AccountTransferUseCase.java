package buckpal.application.domain.service;

import buckpal.application.port.in.ForSendMoneyAccount;
import buckpal.application.port.out.ForGetAccount;
import buckpal.application.port.out.ForUpdateAccount;
import buckpal.common.IAccountLock;
import buckpal.common.IUseCase;
import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.service.exception.ThresholdExceededException;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@IUseCase
@Transactional
public class AccountTransferUseCase implements ForSendMoneyAccount {

	private final ForGetAccount loadPort;
	private final IAccountLock accountLock;
	private final ForUpdateAccount updatePort;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean sendMoney(SendMoneyCommand command) {

		checkThreshold(command);

		Account sourceAccount = loadPort.loadAccount(
				command.sourceAccountId());

		Account targetAccount = loadPort.loadAccount(
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

		updatePort.updateActivities(sourceAccount);
		updatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(SendMoneyCommand command) {
		if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(),
					command.money());
		}
	}

}
