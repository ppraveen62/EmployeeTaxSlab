package CodeTest.EmployeeTaxSlab.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @NotEmpty(message = "firstName cant be Empty or Null")
    private String firstName;
    @NotEmpty(message = "lastName cant be Empty or Null")
    private String lastName;
    @NotEmpty(message = "email cant be Empty or Null")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "phoneNumber cant be Empty or Null")
    private List<String> phoneNumber;
    @NotNull(message = "doj cant be Null")
    private Date doj;
    @NotNull(message = "salary cant be Null")
    @Min(value = 0, message = "Salary must be greater than zero")
    private Double salary;
}
