package practice.studysecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.studysecurity.domain.Account;

public interface UserRepository extends JpaRepository<Account, Long> {

  Account findByUsername(String username);
  
}
