package com.aspire.miniaspire.storage;

import com.aspire.miniaspire.domain.LoanApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoanApplicationStore {
    List<LoanApplication> loanApplications = new ArrayList<>();

    public void add(LoanApplication loanApplication) {
        loanApplications.add(loanApplication);
    }

    public List<LoanApplication> getByUserId(Integer userId){
        List<LoanApplication> result = new ArrayList<>();

        for(LoanApplication loanApplication: loanApplications){
            if(loanApplication.getUserId().equals(userId)){
                result.add(loanApplication);
            }
        }

        return result;
    }

    public LoanApplication getById(int id){
        for(LoanApplication loanApplication: loanApplications){
            if(loanApplication.getId() == id){
                return loanApplication;
            }
        }
        return null;
    }

    public int getTotalCountOfLoanApplications(){
        return loanApplications.size();
    }
}
