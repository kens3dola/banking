package bankingsystem.backend.service;

import bankingsystem.backend.config.JwtTokenUtil;
import bankingsystem.backend.dao.SavingRepository;
import bankingsystem.backend.dao.TransactionRepository;
import bankingsystem.backend.dto.SavingMoneyDto;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.Saving;
import bankingsystem.backend.entity.Transaction;
import bankingsystem.backend.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
public class SavingService {

    @Autowired
    SavingRepository savingRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String saveMoney(SavingMoneyDto savingMoneyDto, String token) {
        String user = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.getAccountByAccountNo(user);

        if (account.getBalance() < savingMoneyDto.getAmount()) {
            log.error("Not enough balance in account : {}", account);
            throw new BadRequestException("Not enough balance");
        }

        Saving saving = new Saving();
        saving.setAccount(account);
        saving.setAmount(savingMoneyDto.getAmount());
        saving.setDuration(Optional.ofNullable(savingMoneyDto.getDuration()).orElse(1));
        saving.setStartDate(LocalDateTime.now());
        account.setBalance(account.getBalance()-savingMoneyDto.getAmount());
        Transaction transaction = new Transaction();
        transaction.setAmount(savingMoneyDto.getAmount());
        transaction.setTransferFrom(account.getAccountNo());
        transaction.setDate(new Date());
        transaction.setType("saving");
        transactionRepository.save(transaction);
        savingRepository.save(saving);
        return "DONE";
    }


    public List<Saving> savingHistory(String token) {
        String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.getAccountByAccountNo(accountNo);
        List<Saving> savings = new ArrayList<>();
        savings.addAll(savingRepository.findAllByAccountIdOrderByStartDateDesc(account.getId()));
        return savings;
    }
}
