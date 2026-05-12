package padthai.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class, EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  Map<String, String> notFound(Exception e) {
    return Map.of("message", e.getMessage());
  }
}
