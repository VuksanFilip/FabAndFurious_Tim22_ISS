package rs.ac.uns.ftn.informatika.jpa.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;

import java.util.List;

@RestControllerAdvice
public class ValidationErrors {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<MessageDTO> handleValidationError(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder sb = new StringBuilder("");

        for (FieldError fieldError : fieldErrors ) {
            sb.append("Field ");
            sb.append(fieldError.getField()).append(": ");
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return new ResponseEntity<>(new MessageDTO(sb.toString()), HttpStatus.BAD_REQUEST);
    }
}
