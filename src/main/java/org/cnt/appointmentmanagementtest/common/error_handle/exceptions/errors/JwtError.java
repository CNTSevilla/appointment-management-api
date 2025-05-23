package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.IError;
import org.springframework.http.HttpStatus;

/**
 * Enumeración de errores relacionados con JWT (JSON Web Tokens).
 */
public enum JwtError implements IError {

    TOKEN_EXPIRED("El token JWT ha expirado", "-200", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNATURE("La firma del token JWT no es válida", "-201", HttpStatus.UNAUTHORIZED),
    MALFORMED_TOKEN("El token JWT está mal formado", "-202", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_TOKEN("El token JWT no está soportado", "-203", HttpStatus.BAD_REQUEST),
    CLAIMS_EMPTY("El token JWT no contiene claims válidos", "-204", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED("La autenticación basada en token JWT ha fallado", "-205", HttpStatus.FORBIDDEN),
    TOKEN_NOT_FOUND("No se ha proporcionado un token JWT", "-206", HttpStatus.UNAUTHORIZED),
    UNKNOWN_JWT_ERROR("Error desconocido relacionado con token JWT", "-207", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    JwtError(String message, String code, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}