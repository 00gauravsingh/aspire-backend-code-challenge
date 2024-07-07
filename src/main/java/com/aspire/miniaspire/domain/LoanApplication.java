package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.LoanApplicationStatus;
import lombok.Data;

import java.util.ArrayList;

@Data
public class LoanApplication {
    private int id;
    private Double amountRequired;
    private int loanTerm;
    private Integer userId;
    private LoanApplicationStatus status;
    private Integer approvedByUserId;
    private ArrayList<LoanRepayment> loanRepayments = new ArrayList<>();
}
