package com.aspire.miniaspire.controllers.dto;

import lombok.Data;

@Data
public class LoanApplicationRequest {
    Double amount;
    int loanTerm;
}
