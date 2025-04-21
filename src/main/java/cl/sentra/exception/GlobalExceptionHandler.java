package cl.sentra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Cuerpo del request no válido", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEmailFormatException(InvalidEmailFormatException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Formato de correo electrónico no válido", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordFormatException(InvalidPasswordFormatException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("La contraseña no cumple con los requisitos de formato|La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula y un número.", ex.getMessage());
    return ResponseEntity.badRequest().body(errorResponse);
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}