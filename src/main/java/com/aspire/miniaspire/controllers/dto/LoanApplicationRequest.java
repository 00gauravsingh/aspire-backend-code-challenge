package com.aspire.miniaspire.controllers.dto;

import lombok.Data;

@Data
public class LoanApplicationRequest {
    private Double amount;
    private int loanTerm;
}
