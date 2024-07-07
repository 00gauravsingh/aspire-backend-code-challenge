package com.aspire.miniaspire.controllers;

import com.aspire.miniaspire.controllers.dto.LoanApplicationRequest;
import com.aspire.miniaspire.controllers.dto.LoanApprovalRequest;
import com.aspire.miniaspire.controllers.dto.LoanRepaymentRequest;
import com.aspire.miniaspire.domain.LoanApplication;
import com.aspire.miniaspire.domain.User;
import com.aspire.miniaspire.enums.UserType;
import com.aspire.miniaspire.services.LoanApplicationService;
import com.aspire.miniaspire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    UserService userService;

    @Autowired
    LoanApplicationService loanApplicationService;

    @PostMapping("/create-application")
    public ResponseEntity<?> createLoanApplication(@RequestHeader("Username") String username,
                                                   @RequestHeader("Password") String password,
                                                   @RequestBody LoanApplicationRequest loanApplicationRequest) {
        User user = userService.getUserByCreds(username, password);
        if(user != null && user.getUserType().equals(UserType.CUSTOMER)){
            return ResponseEntity.ok(loanApplicationService.createLoanApplication(loanApplicationRequest.getAmount(), loanApplicationRequest.getLoanTerm(), user.getId()));
        }
        return ResponseEntity.ok("No matching customer found!");
    }

    @PostMapping("/approve-application")
    public ResponseEntity<?> approveLoanApplication(@RequestHeader("Username") String username,
                                                                  @RequestHeader("Password") String password,
                                                                  @RequestBody LoanApprovalRequest loanApprovalRequest) {

        User user = userService.getUserByCreds(username, password);
        if(user != null && user.getUserType().equals(UserType.ADMIN)){
            LoanApplication approvedApplication = loanApplicationService.approveLoanApplication(loanApprovalRequest.getLoanApplicationId(), user.getId());
            if(approvedApplication == null) return ResponseEntity.ok("No Matching Application found");
            return ResponseEntity.ok(approvedApplication);
        }
        return ResponseEntity.ok("No matching admin found!");
    }

    @GetMapping("/get-all-application")
    public ResponseEntity<?> getAllLoanApplication(@RequestHeader("Username") String username,
                                                   @RequestHeader("Password") String password) {
        User user = userService.getUserByCreds(username, password);
        if(user != null && user.getUserType().equals(UserType.CUSTOMER)){
            return ResponseEntity.ok(loanApplicationService.getAllLoanApplications(user.getId()));
        }
        return ResponseEntity.ok("No matching customer found!");
    }

    @PostMapping("/add-repayment")
    public ResponseEntity<?> addLoanRepayment(@RequestHeader("Username") String username,
                                              @RequestHeader("Password") String password,
                                              @RequestBody LoanRepaymentRequest loanRepaymentRequest) {
        User user = userService.getUserByCreds(username, password);
        if(user != null && user.getUserType().equals(UserType.CUSTOMER)){
            return ResponseEntity.ok(loanApplicationService.addLoanRepayment(loanRepaymentRequest, user.getId()));
        }
        return ResponseEntity.ok("No matching customer found!");
    }
}
