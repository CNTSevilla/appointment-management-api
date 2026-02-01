package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;

public class UserException extends ApiException {
    public UserException(IAPIError error, String message) {
        super(error, message);
    }
}
