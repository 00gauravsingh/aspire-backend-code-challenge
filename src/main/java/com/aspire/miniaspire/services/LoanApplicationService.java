package com.aspire.miniaspire.services;

import com.aspire.miniaspire.controllers.dto.LoanRepaymentRequest;
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
        int totalApplications = loanApplicationStore.getTotalCountOfLoanApplications();
        LoanApplication loanApplication = populateLoanApplication(amountRequired, loanTerm, totalApplications, userId);
        loanApplicationStore.add(loanApplication);
        return loanApplication;
    }

    public LoanApplication approveLoanApplication(int loanApplicationId, Integer userId){
        LoanApplication loanApplication = loanApplicationStore.getById(loanApplicationId);
        if (loanApplication != null) {
            if(loanApplication.getStatus().equals(LoanApplicationStatus.PENDING)){
                loanApplication.setStatus(LoanApplicationStatus.APPROVED);
                loanApplication.setApprovedByUserId(userId);
                return loanApplication;
            }
        }
        return null;
    }

    public List<LoanApplication> getAllLoanApplications(Integer userId){
        return loanApplicationStore.getByUserId(userId);
    }

    public LoanApplication addLoanRepayment(LoanRepaymentRequest loanRepaymentRequest, Integer userId){
        LoanApplication loanApplication = loanApplicationStore.getById(loanRepaymentRequest.getLoanApplicationId());
        if(markLoanRepayment(loanApplication, userId, loanRepaymentRequest.getAmount())) {
            return loanApplication;
        }
        return null;
    }

    public LoanApplication populateLoanApplication(Double amountRequired, int loanTerm, int totalApplications, Integer userId){
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(totalApplications+1);
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

        return loanApplication;
    }

    public boolean markLoanRepayment(LoanApplication loanApplication, Integer userId, Double repaymentAmount){
        if(loanApplication != null && loanApplication.getStatus().equals(LoanApplicationStatus.APPROVED) && loanApplication.getUserId().equals(userId)){
            List<LoanRepayment> loanRepayments = loanApplication.getLoanRepayments();
            for(int i=0; i<loanRepayments.size(); i++){
                if(loanRepayments.get(i).getAmount() <= repaymentAmount && loanRepayments.get(i).getStatus().equals(LoanRepaymentStatus.PENDING)){
                    loanRepayments.get(i).setStatus(LoanRepaymentStatus.PAID);
                    if(i==loanRepayments.size()-1) loanApplication.setStatus(LoanApplicationStatus.PAID);
                    return true;
                }
            }
        }
        return false;
    }
}
