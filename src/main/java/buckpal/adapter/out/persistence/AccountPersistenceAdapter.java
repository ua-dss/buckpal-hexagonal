package buckpal.adapter.out.persistence;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Activity;
import buckpal.application.port.out.LoadAccountPort;
import buckpal.application.port.out.UpdateAccountStatePort;
import buckpal.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PersistenceAdapter
class AccountPersistenceAdapter implements
		LoadAccountPort,
		UpdateAccountStatePort {

	private final SpringDataAccountRepository accountRepository;
	private final ActivityRepository activityRepository;
	private final AccountMapper accountMapper;

	@Override
	public Account loadAccount(AccountId accountId) {

		AccountJpaEntity account =
				accountRepository.findById(accountId.getValue())
						.orElseThrow(() -> new EntityNotFoundException(
								"Account with ID " + accountId.getValue() + " not found"));

		List<ActivityJpaEntity> activities =
				activityRepository.findByOwner(
						accountId.getValue());

		return accountMapper.mapToDomainEntity(
				account,
				activities);

	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll().stream()
				.map(accountJpaEntity -> {
					List<ActivityJpaEntity> activities =
							activityRepository.findByOwner(
									accountJpaEntity.getId());

					return accountMapper.mapToDomainEntity(
							accountJpaEntity,
							activities);
				})
				.collect(Collectors.toList());
	}

	@Override
	public void updateActivities(Account account) {
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
	}

	@Override
	public AccountId createAccount(Account account) {
		AccountJpaEntity accountJpaEntity = new AccountJpaEntity();
		accountJpaEntity.setBaselineBalance(account.getBaselineBalance().getAmount().longValue());
		AccountJpaEntity savedAccount = accountRepository.save(accountJpaEntity);
		return new AccountId(savedAccount.getId());
	}

}
