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

        Calendar currentYearStartCalendar = Calendar.getInstance();
        currentYearStartCalendar.set(Calendar.MONTH, Calendar.APRIL); // April
        currentYearStartCalendar.set(Calendar.DAY_OF_MONTH, 1); // 1st day of April
        currentYearStartCalendar.add(Calendar.YEAR, -1); // Go back one year
        Date curntFinancalyearSartDate = currentYearStartCalendar.getTime();

        Calendar dojCalendar = Calendar.getInstance();
        dojCalendar.setTime(doj);
        int dojYear = dojCalendar.get(Calendar.YEAR);
        int dojMonth = dojCalendar.get(Calendar.MONTH)+1;
        int dojDay = dojCalendar.get(Calendar.DAY_OF_MONTH);
        int currentYear = currentYearStartCalendar.get(Calendar.YEAR);
        
        int daysWorked = 0;
        
        if(doj.before(curntFinancalyearSartDate)){
            daysWorked = 12*30;
        }
        if(doj.after(curntFinancalyearSartDate) && dojYear<currentYear){
            daysWorked = (13-dojMonth +3)*30;
            if(dojDay>1){
                daysWorked = daysWorked - (30-(dojDay+1));
            }
        }
        if(doj.after(curntFinancalyearSartDate) && dojYear==currentYear){
            daysWorked = (4-dojMonth)*30;
            if(dojDay>1){
                daysWorked = daysWorked - (30-(dojDay+1));
            }
        }
        Double yearSalary=employee.getSalary()/30 *daysWorked;

        Double taxableIncome =  yearSalary;

        double tax = 0;
        if (taxableIncome > 1000000.0) {
            tax += (taxableIncome - 1000000.0) * 0.2;
            taxableIncome = 1000000.0;
        }
        if (taxableIncome > 500000.0) {
            tax += (taxableIncome - 500000.0) * 0.1;
            taxableIncome = 500000.0;
        }
        if (taxableIncome > 250000.0) {
            tax += (taxableIncome - 250000.0) * 0.05;
        }

        EmployeeTaxDetails taxDetails = getTaxDetails(employee, tax, yearSalary);

        return taxDetails;
    }

    private static EmployeeTaxDetails getTaxDetails(Employee employee, double tax , Double yearlySalary) {
        double cess = 0;
        //Collect additional 2% cess for the amount more than 2500000 (ex: yearly salary is 2800000 then collect 2% cess for 300000)
        if (yearlySalary > 2500000) {
            cess = 0.02 * (yearlySalary - 2500000);
        }

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