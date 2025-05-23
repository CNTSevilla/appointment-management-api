package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

public class UserException extends RuntimeException {

    public UserException(IError error) {
        super(error.getMessage());
    }
}
