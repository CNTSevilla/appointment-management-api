package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum UserError implements IAPIError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "-300","El usuario no fue encontrado"),
    DUPLICATE_USER(HttpStatus.CONFLICT, "-301","El usuario ya existe"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED,"-302", "Credenciales inválidas para iniciar sesión"),
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "-303","La cuenta del usuario está bloqueada"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "-304", "Datos de entrada del usuario no válidos"),
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "-305", "El usuario no está autorizado para realizar esta operación"),
    PASSWORD_TOO_WEAK(HttpStatus.BAD_REQUEST, "-306", "La contraseña del usuario no cumple con los criterios mínimos"),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "-307", "La sesión del usuario ha expirado"),
    UNKNOWN_USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "-308","Error desconocido relacionado con operación de usuario");

    private final HttpStatus httpStatus;
    private final String code;
    private final String reason;

}