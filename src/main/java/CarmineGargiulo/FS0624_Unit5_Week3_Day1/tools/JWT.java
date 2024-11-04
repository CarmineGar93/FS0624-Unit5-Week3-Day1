package CarmineGargiulo.FS0624_Unit5_Week3_Day1.tools;

import CarmineGargiulo.FS0624_Unit5_Week3_Day1.entities.Employee;
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
}
