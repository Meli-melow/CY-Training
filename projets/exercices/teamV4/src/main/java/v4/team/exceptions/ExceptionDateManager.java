package v4.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Cette classe est associée aux exceptions relatives à la gestion des données temporelles.
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExceptionDateManager extends RuntimeException{

    public ExceptionDateManager(String message) { super(message); }

}
