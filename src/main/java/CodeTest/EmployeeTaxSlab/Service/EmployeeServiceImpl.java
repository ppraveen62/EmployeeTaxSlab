package CodeTest.EmployeeTaxSlab.Service;

import CodeTest.EmployeeTaxSlab.DTO.EmployeeDto;
import CodeTest.EmployeeTaxSlab.DTO.EmployeeTaxDetails;
import CodeTest.EmployeeTaxSlab.Entity.Employee;
import CodeTest.EmployeeTaxSlab.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Employee save(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto,Employee.class);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }



    @Override
    public List<EmployeeTaxDetails> getAllEmployeeTaxDetails() {
        List<EmployeeTaxDetails> employeeTaxDetails = new ArrayList<>();
        for(Employee emp : this.getAll()){
            EmployeeTaxDetails employeeTaxDetail = calculateTaxForCurrentYear(emp);
            employeeTaxDetails.add(employeeTaxDetail);
        }
        return employeeTaxDetails;
    }


    private EmployeeTaxDetails calculateTaxForCurrentYear(Employee employee) {
        Date doj = employee.getDoj();
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        double taxableIncome = 0;
        Calendar dojCalendar = Calendar.getInstance();
        dojCalendar.setTime(doj);
        int dojYear = dojCalendar.get(Calendar.YEAR);
        int dojMonth = dojCalendar.get(Calendar.MONTH);

        if(currentYear-dojYear>=2){
            dojYear = currentYear-1;
        }

        int monthsWorked;
        if (dojYear < currentYear || (dojYear == currentYear && dojMonth < Calendar.APRIL)) {
            // Employee joined before the current financial year starts or joined in the same year before April
            monthsWorked = (currentYear - dojYear) * 12; // Total months in the complete years
            if (dojYear == currentYear) {
                // If the employee joined in the same year, add months from January to March
                monthsWorked += currentMonth - dojMonth;
            } else {
                // If the employee joined before the current year, add months from April to December
                monthsWorked += (Calendar.DECEMBER - Calendar.APRIL + 1);
            }
        } else {
            // Employee joined in the current financial year after April
            monthsWorked = currentMonth - Calendar.APRIL + 1;
        }

        double yearlySalary = employee.getSalary() * 12;

        taxableIncome = yearlySalary -(employee.getSalary() * monthsWorked / 12);


        double tax = 0;
        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.2;
            taxableIncome = 1000000;
        }
        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.1;
            taxableIncome = 500000;
        }
        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05;
        }

        EmployeeTaxDetails taxDetails = getTaxDetails(employee, tax);

        return taxDetails;
    }

    private static EmployeeTaxDetails getTaxDetails(Employee employee, double tax) {
        double cess = 0;
        //Collect additional 2% cess for the amount more than 2500000 (ex: yearly salary is 2800000 then collect 2% cess for 300000)
        if (employee.getSalary() > 2500000) {
            cess = 0.02 * (employee.getSalary() - 2500000);
        }
        double yearlySalary = employee.getSalary() * 12;

        EmployeeTaxDetails taxDetails = new EmployeeTaxDetails();
        taxDetails.setEmployeeCode(String.valueOf(employee.getEmployeeId()));
        taxDetails.setFirstName(employee.getFirstName());
        taxDetails.setLastName(employee.getLastName());
        taxDetails.setYearlySalary(yearlySalary);
        taxDetails.setTaxAmount(tax);
        taxDetails.setCessAmount(cess);
        return taxDetails;
    }


}