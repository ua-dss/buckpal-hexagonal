package buckpal.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import buckpal.application.domain.model.Account;
import buckpal.application.port.in.AccountListAllUseCase;
import buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@WebAdapter
@RestController
@RequiredArgsConstructor
class AccountListAllController {

	private final AccountListAllUseCase listAccountsUseCase;

	@GetMapping(path = "/accounts")
	ListResponse listAccounts() {

		try {
			List<AccountInfo> accounts = listAccountsUseCase.listAccounts().stream()
					.map(account -> new AccountInfo(
							account.getId().orElse(null).getValue(),
							account.calculateBalance().getAmount().longValue()))
					.collect(Collectors.toList());
			return new ListResponse(true, accounts, null);
		} catch (Exception e) {
			return new ListResponse(false, null, e.getMessage());
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record ListResponse(boolean success, List<AccountInfo> accounts, String errorMessage) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	record AccountInfo(Long id, Long balance) {
	}

}
