package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.LoanRepaymentStatus;
import lombok.Data;

@Data
public class LoanRepayment {
    private int id;
    private Double amount;
    private LoanRepaymentStatus status;
    private Integer userId;
}
