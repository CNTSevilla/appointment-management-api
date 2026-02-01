package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IError;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailError implements IError {


    CONNECTION_REFUSED("-1", "No se ha podido conectar con el servidor de correo"),
    INVALID_CREDENTIALS("-2", "Credenciales inválidas para el servidor de correo"),
    EMAIL_NOT_FOUND("-3", "La dirección de correo electrónico no existe"),
    ATTACHMENT_TOO_LARGE("-4", "El tamaño de los archivos adjuntos excede el límite permitido"),
    SERVER_TIMEOUT("-5", "El servidor de correo no respondió a tiempo"),
    UNKNOWN_ERROR("-6", "Se ha producido un error desconocido enviando el correo");

    private final String code;
    private final String reason;
}
