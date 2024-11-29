package chauduong.myapp.configuration;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        // Lấy JWT token từ header Authorization
        String jwtToken = getJwtFromRequest(request);

        if (jwtToken != null && jwtProvider.validateToken(jwtToken)) {
            Claims claims = jwtProvider.getClaimsFromToken(jwtToken);
            String username = claims.getSubject();
            // Tạo Authentication từ thông tin người dùng (username)
            if (username != null) {
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(username);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);  // Tiến hành xử lý yêu cầu tiếp theo
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Trả về phần token sau từ "Bearer "
        }
        return null;
    }
}
