package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.LoanRepaymentStatus;
import lombok.Data;

@Data
public class LoanRepayment {
    int id;
    Double amount;
    LoanRepaymentStatus status;
    Integer userId;
}
