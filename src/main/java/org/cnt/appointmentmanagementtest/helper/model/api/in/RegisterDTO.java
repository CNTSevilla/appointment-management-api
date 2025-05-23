package org.cnt.appointmentmanagementtest.helper.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String username;
    private String password;

    private String name;
    private String phone;
    private String email;
}