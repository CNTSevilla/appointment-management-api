package org.cnt.appointmentmanagementtest.appointment.model.api.out;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentDTO {

    private UUID id;
    private String comment;
    private ZonedDateTime date;
    private UUID helper;
}
