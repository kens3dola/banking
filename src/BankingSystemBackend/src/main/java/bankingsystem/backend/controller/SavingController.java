package bankingsystem.backend.controller;

import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.Response;
import bankingsystem.backend.dto.SavingMoneyDto;
import bankingsystem.backend.entity.Saving;
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
public class SavingController {

    @Autowired
    SavingService service;

    @PostMapping("/saving")
    public ResponseEntity<Response> transferMoney(@RequestBody SavingMoneyDto savingMoneyDto, HttpServletRequest request) {
        return ResponseEntity.ok(new Response(Constants.SUCCESS, service.saveMoney(savingMoneyDto, request.getHeader("token"))));
    }
    @GetMapping("/saving/history")
    public ResponseEntity<List<Saving>> savingHistory(HttpServletRequest request) {
        return ResponseEntity.ok(service.savingHistory(request.getHeader("token")));
    }
}
