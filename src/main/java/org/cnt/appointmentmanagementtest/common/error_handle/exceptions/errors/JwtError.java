package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;
import org.springframework.http.HttpStatus;

/**
 * Enumeración de errores relacionados con JWT (JSON Web Tokens).
 */
@AllArgsConstructor
@Getter
public enum JwtError implements IAPIError {

    TOKEN_EXPIRED( HttpStatus.UNAUTHORIZED, "-200", "El token JWT ha expirado"),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "-201", "La firma del token JWT no es válida"),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "-202","El token JWT está mal formado"),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "-203", "El token JWT no está soportado"),
    CLAIMS_EMPTY(HttpStatus.UNAUTHORIZED, "-204", "El token JWT no contiene claims válidos"),
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "-205","La autenticación basada en token JWT ha fallado"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "-206","No se ha proporcionado un token JWT"),
    UNKNOWN_JWT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "-207", "Error desconocido relacionado con token JWT");

    private final HttpStatus httpStatus;
    private final String code;
    private final String reason;

}