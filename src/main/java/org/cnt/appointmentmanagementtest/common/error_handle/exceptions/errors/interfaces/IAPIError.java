package org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface IAPIError extends IError {

    HttpStatus getHttpStatus();

}