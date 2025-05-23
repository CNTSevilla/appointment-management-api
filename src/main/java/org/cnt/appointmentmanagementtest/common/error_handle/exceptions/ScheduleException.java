package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

public class ScheduleException extends RuntimeException {
    public ScheduleException(IError error) {
        super(error.getMessage());
    }
}
