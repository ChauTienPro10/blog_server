package chauduong.myapp.repository;

import chauduong.myapp.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
