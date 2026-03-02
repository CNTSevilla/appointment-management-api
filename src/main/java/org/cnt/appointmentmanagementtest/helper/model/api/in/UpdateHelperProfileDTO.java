package org.cnt.appointmentmanagementtest.helper.model.api.in;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHelperProfileDTO {

    private String username;
    private String phone;
    private String email;
    private String currentPassword;
    private String newPassword;

}
