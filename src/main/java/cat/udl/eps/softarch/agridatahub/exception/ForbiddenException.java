package cat.udl.eps.softarch.agridatahub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason = "Unauthorized")
public class ForbiddenException extends RuntimeException{
}