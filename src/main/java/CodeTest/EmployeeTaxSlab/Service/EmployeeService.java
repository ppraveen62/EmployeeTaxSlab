package CodeTest.EmployeeTaxSlab.Service;

import CodeTest.EmployeeTaxSlab.DTO.EmployeeDto;
import CodeTest.EmployeeTaxSlab.DTO.EmployeeTaxDetails;
import CodeTest.EmployeeTaxSlab.Entity.Employee;

import java.util.List;


public interface EmployeeService {
    public Employee save(EmployeeDto employeeDto);

    public List<Employee> getAll();

    public List<EmployeeTaxDetails> getAllEmployeeTaxDetails();

}
