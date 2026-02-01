package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SecurityError implements IAPIError {

    LOGIN_ERROR(HttpStatus.UNAUTHORIZED, "-1", "No se ha podido establecer el login satisfactoriamente"),
    REGISTER_ERROR(HttpStatus.UNAUTHORIZED, "-2", "No se ha podido registrar el usuario satisfactoriamente"),
    REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "-3", "No se ha podido actualizar el token de refresco satisfactoriamente"),
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "-4", "No se ha podido actualizar el token de acceso satisfactoriamente"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "-5", "El token ha expirado");

    private final HttpStatus httpStatus;
    private final String code;
    private final String reason;
}
