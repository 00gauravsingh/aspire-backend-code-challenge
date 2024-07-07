package com.aspire.miniaspire.services;

import com.aspire.miniaspire.domain.LoanApplication;
import com.aspire.miniaspire.domain.LoanRepayment;
import com.aspire.miniaspire.enums.LoanApplicationStatus;
import com.aspire.miniaspire.enums.LoanRepaymentStatus;
import com.aspire.miniaspire.storage.LoanApplicationStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    LoanApplicationStore loanApplicationStore;

    public LoanApplication createLoanApplication(Double amountRequired, int loanTerm, Integer userId){
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(loanApplicationStore.getTotalCountOfLoanApplications()+1);
        loanApplication.setStatus(LoanApplicationStatus.PENDING);
        loanApplication.setAmountRequired(amountRequired);
        loanApplication.setLoanTerm(loanTerm);
        loanApplication.setUserId(userId);

        for(int i=0; i< loanTerm; i++){
            LoanRepayment loanRepayment = new LoanRepayment();
            loanRepayment.setUserId(userId);
            loanRepayment.setStatus(LoanRepaymentStatus.PENDING);
            loanRepayment.setAmount(amountRequired/loanTerm);
            loanApplication.getLoanRepayments().add(loanRepayment);
        }

        loanApplicationStore.add(loanApplication);

        return loanApplication;
    }

    public LoanApplication approveLoanApplication(int loanApplicationId, Integer userId){
        LoanApplication loanApplication = loanApplicationStore.getById(loanApplicationId);
        if (loanApplication != null) {
            loanApplication.setStatus(LoanApplicationStatus.APPROVED);
            loanApplication.setApprovedByUserId(userId);
            return loanApplication;
        }
        return null;
    }

    public List<LoanApplication> getAllLoanApplications(Integer userId){
        return loanApplicationStore.getByUserId(userId);
    }
}
