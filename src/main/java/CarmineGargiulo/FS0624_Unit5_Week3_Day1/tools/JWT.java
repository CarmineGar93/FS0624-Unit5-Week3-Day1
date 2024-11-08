package CarmineGargiulo.FS0624_Unit5_Week3_Day1.tools;

import CarmineGargiulo.FS0624_Unit5_Week3_Day1.entities.Employee;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWT {
    @Value("${jwt.secret}")
    private String secret;

    public String genereteToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .subject(String.valueOf(employee.getEmployeeId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token. You must re-do login");
        }
    }

   /* public String getEmployeeIdByToken(HttpHeaders headers, String secret) {
        String token = headers.get("Authorization").getFirst();
        String jwt = token.replace("Bearer ", "");
        String userId =
                Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(jwt).getPayload()
                .toString();
        return userId;
    }*/
}
