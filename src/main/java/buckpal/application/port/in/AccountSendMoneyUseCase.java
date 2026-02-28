package buckpal.application.port.in;

public interface AccountSendMoneyUseCase {

	boolean sendMoney(AccountSendMoneyCommand command);

}
