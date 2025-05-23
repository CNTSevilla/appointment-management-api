package org.cnt.appointmentmanagementtest.person_in_need.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInNeedCompleteInfoDTO {


    private String name;
    private String phone;
    private String email;

    private List<AppointmentCompleteInfoDTO> appointments;
}
