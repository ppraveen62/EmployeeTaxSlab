package CodeTest.EmployeeTaxSlab.Service;

import CodeTest.EmployeeTaxSlab.DTO.EmployeeDto;
import CodeTest.EmployeeTaxSlab.DTO.EmployeeTaxDetails;
import CodeTest.EmployeeTaxSlab.Entity.Employee;
import CodeTest.EmployeeTaxSlab.Exception.BadRequestException;

import java.util.List;


public interface EmployeeService {
    Employee save(EmployeeDto employeeDto);

    List<Employee> getAll();

    Employee getById(int id) throws BadRequestException;

    List<EmployeeTaxDetails> getAllEmployeeTaxDetails();

}
