package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.LoanApplicationStatus;
import lombok.Data;

import java.util.ArrayList;

@Data
public class LoanApplication {
    int id;
    Double amountRequired;
    int loanTerm;
    Integer userId;
    LoanApplicationStatus status;
    Integer approvedByUserId;
    ArrayList<LoanRepayment> loanRepayments = new ArrayList<>();
}
