package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IError;
import org.springframework.http.HttpStatus;

/**
 * Enumeración de errores relacionados con operaciones batch programadas.
 */
@AllArgsConstructor
@Getter
public enum BatchError implements IError {

    UNKNOWN_BATCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "-104", "Error desconocido en las operaciones batch programadas");

    private final HttpStatus httpStatus;
    private final String code;
    private final String reason;

}