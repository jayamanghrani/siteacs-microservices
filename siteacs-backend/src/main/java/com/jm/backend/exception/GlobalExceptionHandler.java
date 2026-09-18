package com.jm.backend.exception;

import com.jm.backend.dto.APIResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*@RestControllerAdvice ko Spring ek special bean maan kar uske @ExceptionHandler methods
ko internally register kar leta hai map me.
Controller me exception aaye to Spring registered handlers me matching exception
type dhundhkar automatically correct handler method call kar deta hai.*/

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponseDTO> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponseDTO(false,ex.getMessage()));
    }

    @ExceptionHandler(ClientInactiveException.class)
    public ResponseEntity<APIResponseDTO> handleClientInactive(ClientInactiveException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new APIResponseDTO(false,ex.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<APIResponseDTO> handleInvalidToken(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponseDTO(false, ex.getMessage()));
    }

    // JWT library khud jo exceptions throw karti hai, unhe bhi yahan catch karo
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponseDTO> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponseDTO(false, "Token has expired, please login again"));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponseDTO> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponseDTO(false, "Invalid token"));
    }

    // Spring Security khud jo 403 deta hai role-mismatch pe, use bhi customize kar sakte ho
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new APIResponseDTO(false, "You don't have permission to access this resource"));
    }

    // Sabसे aakhri mein - koi aur/anexpected exception ho to bhi crash na ho, generic response de
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponseDTO(false, "Something went wrong. Please try again later."));
    }

}
