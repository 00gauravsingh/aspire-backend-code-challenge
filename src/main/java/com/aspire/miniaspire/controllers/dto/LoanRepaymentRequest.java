package com.aspire.miniaspire.controllers.dto;

import lombok.Data;

@Data
public class LoanRepaymentRequest {
    private Double amount;
    private int loanApplicationId;
}
