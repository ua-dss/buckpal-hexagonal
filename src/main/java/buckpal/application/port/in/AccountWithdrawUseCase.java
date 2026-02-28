package buckpal.application.port.in;

public interface AccountWithdrawUseCase {

	boolean withdraw(AccountWithdrawCommand command);

}
