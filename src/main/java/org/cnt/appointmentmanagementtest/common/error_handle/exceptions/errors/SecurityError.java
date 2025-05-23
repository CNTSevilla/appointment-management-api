package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.IError;
import org.springframework.http.HttpStatus;

public enum SecurityError implements IError {

    LOGIN_ERROR(HttpStatus.UNAUTHORIZED, "No se ha podido establecer el login satisfactoriamente", "-1"),
    REGISTER_ERROR(HttpStatus.UNAUTHORIZED, "No se ha podido registrar el usuario satisfactoriamente", "-2"),
    REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "No se ha podido actualizar el token de refresco satisfactoriamente", "-3"),
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "No se ha podido actualizar el token de acceso satisfactoriamente", "-4"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "El token ha expirado", "-5");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    SecurityError(HttpStatus httpStatus, String message, String code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }


    public HttpStatus getHttpCode() {
        return this.httpStatus;
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
