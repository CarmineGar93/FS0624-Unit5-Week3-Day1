package CarmineGargiulo.FS0624_Unit5_Week3_Day1.controllers;

import CarmineGargiulo.FS0624_Unit5_Week3_Day1.dto.EmployeeDTO;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.entities.Employee;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.exceptions.BadRequestException;
import CarmineGargiulo.FS0624_Unit5_Week3_Day1.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public List<Employee> getEmployees() {
        return employeesService.findAllEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return employeesService.saveEmployee(body);
    }

    @GetMapping("/{employeeId}")
    public Employee findEmployee(@PathVariable UUID employeeId) {
        return employeesService.findEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return employeesService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeesService.findEmployeeByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public void uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID employeeId) {
        employeesService.uploadAvatar(file, employeeId);
    }
}
