package com.aspire.miniaspire.controllers.dto;

import lombok.Data;

@Data
public class LoanRepaymentRequest {
    Double amount;
    int loanApplicationId;
}
