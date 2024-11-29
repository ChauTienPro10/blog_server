package chauduong.myapp.repository;

import chauduong.myapp.entity.Account;
import chauduong.myapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
