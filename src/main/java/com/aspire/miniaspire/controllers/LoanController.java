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
        if(loanApplicationRequest.getAmount() <= 0 || loanApplicationRequest.getLoanTerm() < 0){
            return ResponseEntity.badRequest().body("Invalid Amount or Loan Term provided");
        }
        try {
            User user = userService.getUserByCreds(username, password);
            if (user != null && user.getUserType().equals(UserType.CUSTOMER)) {
                return ResponseEntity.ok(loanApplicationService.createLoanApplication(loanApplicationRequest.getAmount(), loanApplicationRequest.getLoanTerm(), user.getId()));
            }
            return ResponseEntity.ok("No matching customer found!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/approve-application")
    public ResponseEntity<?> approveLoanApplication(@RequestHeader("Username") String username,
                                                    @RequestHeader("Password") String password,
                                                    @RequestBody LoanApprovalRequest loanApprovalRequest) {
        if(loanApprovalRequest.getLoanApplicationId() < 0) return ResponseEntity.badRequest().body("Invalid Application Id provided");
        try {
            User user = userService.getUserByCreds(username, password);
            if (user != null && user.getUserType().equals(UserType.ADMIN)) {
                LoanApplication approvedApplication = loanApplicationService.approveLoanApplication(loanApprovalRequest.getLoanApplicationId(), user.getId());
                if (approvedApplication == null)
                    return ResponseEntity.ok("No Matching Application found in PENDING state");
                return ResponseEntity.ok(approvedApplication);
            }
            return ResponseEntity.ok("No matching admin found!");
        } catch (Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

    @GetMapping("/get-all-application")
    public ResponseEntity<?> getAllLoanApplication(@RequestHeader("Username") String username,
                                                   @RequestHeader("Password") String password) {
        try {
            User user = userService.getUserByCreds(username, password);
            if (user != null && user.getUserType().equals(UserType.CUSTOMER)) {
                return ResponseEntity.ok(loanApplicationService.getAllLoanApplications(user.getId()));
            }
            return ResponseEntity.ok("No matching customer found!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add-repayment")
    public ResponseEntity<?> addLoanRepayment(@RequestHeader("Username") String username,
                                              @RequestHeader("Password") String password,
                                              @RequestBody LoanRepaymentRequest loanRepaymentRequest) {
        if(loanRepaymentRequest.getAmount() <= 0 || loanRepaymentRequest.getLoanApplicationId() < 0){
            return ResponseEntity.badRequest().body("Invalid Amount or Application ID provided");
        }
        try{
            User user = userService.getUserByCreds(username, password);
            if(user != null && user.getUserType().equals(UserType.CUSTOMER)){
                LoanApplication loanApplication = loanApplicationService.addLoanRepayment(loanRepaymentRequest, user.getId());
                if(loanApplication == null) return ResponseEntity.ok("No PENDING repayments for the given application ID for this user");
                return ResponseEntity.ok(loanApplication);
            }
            return ResponseEntity.ok("No matching customer found!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
