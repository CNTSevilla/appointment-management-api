package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IError;

@Getter
public class ScheduleException extends RuntimeException {
    private final IError error;
    private final String message;

    public ScheduleException(IError error, String message) {
        super(message);
        this.error = error;
        this.message = message;
    }
}
