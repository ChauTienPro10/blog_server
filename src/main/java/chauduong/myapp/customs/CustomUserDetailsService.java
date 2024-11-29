package chauduong.myapp.customs;

import chauduong.myapp.entity.Account;
import chauduong.myapp.repository.AccountRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from the database (or another source)
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert the User entity to CustomUserDetails
        return new CustomUserDetails(
                account.getUsername(),
                account.getPassword(),
                account.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole())) // Assuming the roles are stored as entities with a 'name' field
                        .collect(Collectors.toList())
        ) ;

    }
}
