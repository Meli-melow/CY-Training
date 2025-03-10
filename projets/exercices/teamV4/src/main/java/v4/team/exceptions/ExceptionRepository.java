package v4.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Cette classe est associée aux exceptions relatives aux repositories.
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExceptionRepository extends RuntimeException {

    public ExceptionRepository(String message) { super(message); }

}
