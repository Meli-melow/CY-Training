package v3.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExceptionRessourceAbsente extends RuntimeException {

        public ExceptionRessourceAbsente(String message) {
            super(message);
        }

}
