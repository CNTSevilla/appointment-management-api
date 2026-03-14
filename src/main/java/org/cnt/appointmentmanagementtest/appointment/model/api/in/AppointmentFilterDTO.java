package org.cnt.appointmentmanagementtest.appointment.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterDTO {
    ZonedDateTime start;
    ZonedDateTime end;
}
