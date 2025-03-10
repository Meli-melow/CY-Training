package atilla.demo.classes.Exceptions;


import atilla.demo.dto.ErrorEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityNotFoundException.class})
    public @ResponseBody ErrorEntity handleException(EntityNotFoundException exception){
        return new ErrorEntity(null, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ExceptionHandler({EmailExisteDeja.class})
    @ExceptionHandler({EntityExistsException.class})
    public @ResponseBody ErrorEntity handleException(EntityExistsException exception){
        return new ErrorEntity(null, exception.getMessage());
    }









}
