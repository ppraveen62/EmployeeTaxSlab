package CodeTest.EmployeeTaxSlab.Repository;


import CodeTest.EmployeeTaxSlab.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
