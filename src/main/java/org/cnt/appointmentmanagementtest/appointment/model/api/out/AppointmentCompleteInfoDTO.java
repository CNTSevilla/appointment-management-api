package org.cnt.appointmentmanagementtest.appointment.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Priority;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Status;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCompleteInfoDTO {

    private UUID id;
    private ZonedDateTime dateTime;
    private Priority priority;
    private Status status;
    private String personInNeed;
    private String helper;
    private List<GetCommentDTO> comments;
}
