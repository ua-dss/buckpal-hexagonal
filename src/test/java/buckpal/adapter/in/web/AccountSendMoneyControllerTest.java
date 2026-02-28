package buckpal.adapter.in.web;

import buckpal.application.port.in.ForBalanceAccount;
import buckpal.application.port.in.ForSendMoneyAccount;
import buckpal.application.port.in.ForSendMoneyAccount.SendMoneyCommand;
import buckpal.application.domain.model.Account.AccountId;
import buckpal.application.domain.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountSendMoneyController.class)
class AccountSendMoneyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ForSendMoneyAccount sendMoneyUseCase;

	@MockBean
	private ForBalanceAccount accountBalanceUseCase;

	@Test
	void testSendMoney() throws Exception {

		given(accountBalanceUseCase.getBalance(any(ForBalanceAccount.BalanceQuery.class)))
				.willReturn(Money.of(1000L));

		mockMvc.perform(post("/accounts/send")
				.param("sourceAccountId", "41")
				.param("targetAccountId", "42")
				.param("amount", "500")
				.header("Content-Type", "application/json"))
				.andExpect(status().isOk());

		then(sendMoneyUseCase).should()
				.sendMoney(eq(new SendMoneyCommand(
						new AccountId(41L),
						new AccountId(42L),
						Money.of(500L))));
	}

}
