package CodeTest.EmployeeTaxSlab.Controller;

import CodeTest.EmployeeTaxSlab.DTO.EmployeeDto;
import CodeTest.EmployeeTaxSlab.DTO.EmployeeTaxDetails;
import CodeTest.EmployeeTaxSlab.Entity.Employee;
import CodeTest.EmployeeTaxSlab.Service.EmployeeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> save(@Valid @RequestBody EmployeeDto employeeDto){
        Employee save = employeeService.save(employeeDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> getAll = employeeService.getAll();
        return new ResponseEntity<>(getAll,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getById(@RequestParam ("id") int id){
        Employee getAllbyId = employeeService.getById(id);
        return new ResponseEntity<>(getAllbyId,HttpStatus.OK);
    }

    @GetMapping("/tax")
    @Operation(description = "get all the employees tax")
    public ResponseEntity<List<EmployeeTaxDetails>> getTaxDeductionsForCurrentFinancialYear() {
        List<EmployeeTaxDetails> taxDetailsList = employeeService.getAllEmployeeTaxDetails();
        return new ResponseEntity<>(taxDetailsList, HttpStatus.OK);
    }



}
