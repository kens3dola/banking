package bankingsystem.backend.dao;

import bankingsystem.backend.entity.Constant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstantRepository extends JpaRepository<Constant, Long> {
}