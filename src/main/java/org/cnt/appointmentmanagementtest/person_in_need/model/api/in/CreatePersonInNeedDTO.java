package org.cnt.appointmentmanagementtest.person_in_need.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePersonInNeedDTO {
    private String name;
    private String phone;
    private String email;
}
