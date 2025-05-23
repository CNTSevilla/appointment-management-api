package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.IError;
import org.springframework.http.HttpStatus;

/**
 * Enumeración de errores relacionados con las operaciones de usuario en una app de citas.
 */
public enum UserError implements IError {

    USER_NOT_FOUND("El usuario no fue encontrado", "-300", HttpStatus.NOT_FOUND),
    DUPLICATE_USER("El usuario ya existe", "-301", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("Credenciales inválidas para iniciar sesión", "-302", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("La cuenta del usuario está bloqueada", "-303", HttpStatus.FORBIDDEN),
    INVALID_INPUT("Datos de entrada del usuario no válidos", "-304", HttpStatus.BAD_REQUEST),
    USER_NOT_AUTHORIZED("El usuario no está autorizado para realizar esta operación", "-305", HttpStatus.FORBIDDEN),
    PASSWORD_TOO_WEAK("La contraseña del usuario no cumple con los criterios mínimos", "-306", HttpStatus.BAD_REQUEST),
    SESSION_EXPIRED("La sesión del usuario ha expirado", "-307", HttpStatus.UNAUTHORIZED),
    UNKNOWN_USER_ERROR("Error desconocido relacionado con operación de usuario", "-308", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    UserError(String message, String code, HttpStatus httpStatus) {
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
}