package CarmineGargiulo.FS0624_Unit5_Week3_Day1.services;

import CarmineGargiulo.FS0624_Unit5_Week3_Day1.dto.EmployeesLoginDTO;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.entities.Employee;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.exceptions.UnauthorizedException;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private JWT jwt;

    public String generateToken(EmployeesLoginDTO body) {
        Employee employee = employeesService.findEmployeeByEmail(body.email());
        if (employee.getUsername().equals(body.username())) return jwt.genereteToken(employee);
        else throw new UnauthorizedException("Invalid credentials");
    }
}
