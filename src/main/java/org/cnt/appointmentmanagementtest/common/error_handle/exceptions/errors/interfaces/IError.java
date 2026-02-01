package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces;

import org.springframework.http.HttpStatus;

public interface IError {
    String getCode();
    String getReason();

}
