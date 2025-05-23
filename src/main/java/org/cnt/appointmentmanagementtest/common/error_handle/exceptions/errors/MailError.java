package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.IError;

public enum MailError implements IError {


    CONNECTION_REFUSED("No se ha podido conectar con el servidor de correo", "-1"),
    INVALID_CREDENTIALS("Credenciales inválidas para el servidor de correo", "-2"),
    EMAIL_NOT_FOUND("La dirección de correo electrónico no existe", "-3"),
    ATTACHMENT_TOO_LARGE("El tamaño de los archivos adjuntos excede el límite permitido", "-4"),
    SERVER_TIMEOUT("El servidor de correo no respondió a tiempo", "-5"),
    UNKNOWN_ERROR("Se ha producido un error desconocido enviando el correo", "-6");

    private final String message;
    private final String code;

    MailError(String message, String code) {
        this.message = message;
        this.code = code;
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
