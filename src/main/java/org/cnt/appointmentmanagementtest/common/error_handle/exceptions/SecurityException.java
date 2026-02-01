package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IError;

public class SecurityException extends ApiException {
    public SecurityException(IAPIError error, String message) {
        super(error, message);
    }
}
