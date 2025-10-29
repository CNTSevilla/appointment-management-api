package org.cnt.appointmentmanagementtest.helper.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelperProfileDTO {

    private UUID id;
    private String username;
    private String name;
    private String phone;
    private String email;
    private List<String> role;
    private Instant createdAt;  // ✅ nuevo campo
    private List<AppointmentCompleteInfoDTO> pendingAppointments;
    private List<AppointmentCompleteInfoDTO> archivedAppointments;
}
