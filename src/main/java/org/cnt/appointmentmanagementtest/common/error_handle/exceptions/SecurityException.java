package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

public class SecurityException extends RuntimeException {
    public SecurityException(IError error) {
        super(error.getMessage());
    }
}
