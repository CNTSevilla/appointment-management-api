package org.cnt.appointmentmanagementtest.common.error_handle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
    Instant timestamp,
    HttpStatus status,
    String error, //reason phrase
    String code, // error code
    String reason, // fuctional message
    String path
) {}
