package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

import lombok.Getter;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;

@Getter
public abstract class ApiException extends RuntimeException {
    private final IAPIError error;
    private final String message;

    public ApiException(IAPIError error, String message) {
        super();
        this.error = error;
        this.message = message;
    }
}
