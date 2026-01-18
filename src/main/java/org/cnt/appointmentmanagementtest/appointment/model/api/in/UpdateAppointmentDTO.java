package org.cnt.appointmentmanagementtest.appointment.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Priority;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Status;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentDTO {

    private ZonedDateTime dateTime;
    private Priority priority;
    private Status status;
    private UUID personInNeed;
    private UUID helper;
}
