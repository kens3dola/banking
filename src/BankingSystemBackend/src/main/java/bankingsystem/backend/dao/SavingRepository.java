package bankingsystem.backend.dao;

import bankingsystem.backend.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, Long> {
    List<Saving> findAllByAccountIdOrderByStartDateDesc(Integer accountNo);
}