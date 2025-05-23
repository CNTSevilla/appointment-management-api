package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.IError;

/**
 * Enumeración de errores relacionados con operaciones batch programadas.
 */
public enum BatchError implements IError {

    ARCHIVING_FAILED("Error al archivar citas semanalmente", "-100"),
    REMINDER_FAILED("Error al enviar recordatorios diarios de citas", "-101"),
    SCHEDULE_INTERRUPTED("Ejecución programada interrumpida", "-102"),
    INVALID_CRON_EXPRESSION("Expresión cron inválida configurada para la operación batch", "-103"),
    UNKNOWN_BATCH_ERROR("Error desconocido en las operaciones batch programadas", "-104");

    private final String message;
    private final String code;

    BatchError(String message, String code) {
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