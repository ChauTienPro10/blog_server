package chauduong.myapp.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String jwtToken;
    private final User principal;

    // Constructor for creating a token from the JWT token
    public JwtAuthenticationToken(String jwtToken) {
        super(null);  // No authorities at the beginning
        this.jwtToken = jwtToken;
        this.principal = null;  // No principal at the start
        setAuthenticated(false);  // Set authentication as false by default
    }

    // Constructor for creating a token with user details and authorities
    public JwtAuthenticationToken(User principal, String jwtToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwtToken = jwtToken;
        this.principal = principal;
        setAuthenticated(true);
    }

    // Getter for the JWT token
    public String getJwtToken() {
        return jwtToken;
    }

    // Getter for the principal (User)
    @Override
    public Object getPrincipal() {
        return principal;
    }

    // Getter for the credentials (can be used to return any sensitive information like password, etc.)
    @Override
    public Object getCredentials() {
        return jwtToken;  // Can return the JWT token as the credentials
    }
}
