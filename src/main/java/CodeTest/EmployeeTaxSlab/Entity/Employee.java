package CodeTest.EmployeeTaxSlab.Entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @ElementCollection
    private List<String> phoneNumber;
    private Date doj;
    private Double salary;
}
