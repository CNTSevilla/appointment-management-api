package org.cnt.appointmentmanagementtest.helper.model.api.in;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHelperProfileDTO {

    private String clearPassword;

    private String phone;
    private String email;

}
