package v4.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Cette classe est associée aux exceptions relatives aux conversions des mappers.
 */

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class ExceptionMapper extends RuntimeException {

    public ExceptionMapper(String message) { super(message); }

}
