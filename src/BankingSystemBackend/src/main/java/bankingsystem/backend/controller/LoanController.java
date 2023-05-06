package bankingsystem.backend.controller;

import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.LoanDto;
import bankingsystem.backend.dto.Response;
import bankingsystem.backend.dto.SavingMoneyDto;
import bankingsystem.backend.entity.Loan;
import bankingsystem.backend.entity.Saving;
import bankingsystem.backend.service.LoanService;
import bankingsystem.backend.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class LoanController {

    @Autowired
    LoanService service;

    @PostMapping("/loan")
    public ResponseEntity<Response> transferMoney(@RequestBody LoanDto loanDto, HttpServletRequest request) {
        return ResponseEntity.ok(new Response(Constants.SUCCESS, service.loan(loanDto, request.getHeader("token"))));
    }
    @GetMapping("/loan/history")
    public ResponseEntity<List<Loan>> savingHistory(HttpServletRequest request) {
        return ResponseEntity.ok(service.loanHistory(request.getHeader("token")));
    }
}
