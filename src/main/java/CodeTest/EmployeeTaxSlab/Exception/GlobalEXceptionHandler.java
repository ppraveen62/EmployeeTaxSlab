package CodeTest.EmployeeTaxSlab.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class GlobalEXceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> badRequestExceptionhandling(BadRequestException ex){
        Response response = new Response(false,ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> globalExceptionhandling(Exception ex){
        Response response = new Response(false,ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> methodExceptionhandling(MethodArgumentNotValidException ex){
        StringBuilder message = new StringBuilder();
        ex.getAllErrors().forEach(objectError -> {message.append(objectError.getDefaultMessage());
            message.append("; ");});
        Response response = new Response(false,message.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
