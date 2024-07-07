package com.aspire.miniaspire;

import com.aspire.miniaspire.domain.LoanApplication;
import com.aspire.miniaspire.domain.User;
import com.aspire.miniaspire.enums.LoanApplicationStatus;
import com.aspire.miniaspire.enums.UserType;
import com.aspire.miniaspire.services.LoanApplicationService;
import com.aspire.miniaspire.storage.LoanApplicationStore;
import com.aspire.miniaspire.storage.UserStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiniaspireApplicationTests {

	private final UserStore userStore = new UserStore();
	private final LoanApplicationStore loanApplicationStore = new LoanApplicationStore();

	@Test
	void duplicateUsername(){
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("aspire");
		user1.setPassword("aspire");
		user1.setUserType(UserType.ADMIN);
		userStore.addUser(user1);

		User user = new User();
		user.setId(3);
		user.setUsername("aspire");
		user.setPassword("aspire");
		user.setUserType(UserType.ADMIN);
		boolean result = userStore.addUser(user);
		Assertions.assertFalse(result);
	}

	@Test
	void getUserByCreds(){
		User user = new User();
		user.setId(3);
		user.setUsername("aspire");
		user.setPassword("aspire");
		user.setUserType(UserType.ADMIN);
		userStore.addUser(user);

		User user2 = userStore.getUserNameWithCreds("aspire", "aspire");
		Assertions.assertEquals(3, user2.getId());
	}

	@Test
	void getAllUsers(){
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("aspire");
		user1.setPassword("aspire");
		user1.setUserType(UserType.ADMIN);
		userStore.addUser(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setUsername("customer");
		user2.setPassword("customer");
		user2.setUserType(UserType.CUSTOMER);
		userStore.addUser(user2);

		User user3 = new User();
		user3.setId(3);
		user3.setUsername("customer2");
		user3.setPassword("customer2");
		user3.setUserType(UserType.CUSTOMER);
		userStore.addUser(user3);

		int count = userStore.getAll().size();
		Assertions.assertEquals(3, count);
	}

	@Test
	void addLoanApplication(){
		LoanApplicationService loanApplicationService = new LoanApplicationService();
		LoanApplication loanApplication = loanApplicationService.populateLoanApplication(500.0, 5, 0,1);
		loanApplicationStore.add(loanApplication);

		Assertions.assertEquals(1, loanApplicationStore.getTotalCountOfLoanApplications());
	}

	@Test
	void checkLoanRepaymentCalculation(){
		LoanApplicationService loanApplicationService = new LoanApplicationService();
		LoanApplication loanApplication = loanApplicationService.populateLoanApplication(500.0, 5, 0,1);

		Assertions.assertEquals(100.0, loanApplication.getLoanRepayments().get(0).getAmount());
	}

	@Test
	void getAllLoanApplicationsForAUser(){
		LoanApplicationService loanApplicationService = new LoanApplicationService();
		LoanApplication loanApplication1 = loanApplicationService.populateLoanApplication(500.0, 5, 0,1);
		LoanApplication loanApplication2 = loanApplicationService.populateLoanApplication(800.0, 4, 1,2);
		loanApplicationStore.add(loanApplication1);
		loanApplicationStore.add(loanApplication2);

		Assertions.assertEquals(1, loanApplicationStore.getByUserId(1).size());
	}

	@Test
	void approveLoanApplication(){
		LoanApplicationService loanApplicationService = new LoanApplicationService();
		LoanApplication loanApplication = loanApplicationService.populateLoanApplication(500.0, 5, 0,1);
		loanApplicationStore.add(loanApplication);

		LoanApplication loanApplication2 = loanApplicationStore.getById(1);
		Assertions.assertEquals(LoanApplicationStatus.PENDING, loanApplication2.getStatus());
		loanApplication2.setStatus(LoanApplicationStatus.APPROVED);
		loanApplication2.setApprovedByUserId(3);

		LoanApplication loanApplication3 = loanApplicationStore.getById(1);
		Assertions.assertEquals(LoanApplicationStatus.APPROVED, loanApplication3.getStatus());
	}

	@Test
	void addLoanRepayment(){
		LoanApplicationService loanApplicationService = new LoanApplicationService();
		LoanApplication loanApplication = loanApplicationService.populateLoanApplication(500.0, 5, 0,1);
		loanApplicationStore.add(loanApplication);

		LoanApplication loanApplication2 = loanApplicationStore.getById(1);
		boolean result = loanApplicationService.markLoanRepayment(loanApplication2, 1, 100.0);
		Assertions.assertEquals(false, result); //Not in Approved state

		loanApplication2.setStatus(LoanApplicationStatus.APPROVED);
		loanApplication2.setApprovedByUserId(3);

		result = loanApplicationService.markLoanRepayment(loanApplication2, 3, 100.0);
		Assertions.assertEquals(false, result); //Invalid user id

		result = loanApplicationService.markLoanRepayment(loanApplication2, 1, 50.0);
		Assertions.assertEquals(false, result); //Invalid Amount

		result = loanApplicationService.markLoanRepayment(loanApplication2, 1, 100.0);
		Assertions.assertEquals(true, result); //Invalid Amount
	}
}
