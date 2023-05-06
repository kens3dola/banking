package bankingsystem.backend.service;

import bankingsystem.backend.config.JwtTokenUtil;
import bankingsystem.backend.dao.LoanRepository;
import bankingsystem.backend.dao.TransactionRepository;
import bankingsystem.backend.dto.LoanDto;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.Loan;
import bankingsystem.backend.entity.Transaction;
import bankingsystem.backend.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class LoanService {


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String loan(LoanDto loanMoneyDto, String token) {
        String user = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.getAccountByAccountNo(user);

        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setAmount(loanMoneyDto.getAmount());
        loan.setDuration(Optional.ofNullable(loanMoneyDto.getDuration()).orElse(1));
        loan.setStartDate(LocalDateTime.now());
        account.setBalance(account.getBalance()+loanMoneyDto.getAmount());
        Transaction transaction = new Transaction();
        transaction.setAmount(loanMoneyDto.getAmount());
        transaction.setTransferFrom(account.getAccountNo());
        transaction.setDate(new Date());
        transaction.setType("loan");
        transactionRepository.save(transaction);
        loanRepository.save(loan);
        return "DONE";
    }


    public List<Loan> loanHistory(String token) {
        String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.getAccountByAccountNo(accountNo);
        List<Loan> loans = new ArrayList<>();
        loans.addAll(loanRepository.findAllByAccountIdOrderByStartDateDesc(account.getId()));
        return loans;
    }
}
