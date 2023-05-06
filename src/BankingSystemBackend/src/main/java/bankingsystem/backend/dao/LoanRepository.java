package bankingsystem.backend.dao;

import bankingsystem.backend.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByAccountIdOrderByStartDateDesc(Integer id);
}