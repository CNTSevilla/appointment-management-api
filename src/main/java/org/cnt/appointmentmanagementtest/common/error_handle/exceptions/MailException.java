package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

public class MailException extends RuntimeException {
    public MailException(IError error) {
        super(error.getMessage());
    }
}
