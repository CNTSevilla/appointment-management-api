package org.cnt.appointmentmanagementtest.common.error_handle.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface IError {

    String getMessage();
    String getCode();
}