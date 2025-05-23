package org.cnt.appointmentmanagementtest.appointment.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCommentDTO {
    private UUID appointmentId;
    private UUID helper;
    private String comment;
}
