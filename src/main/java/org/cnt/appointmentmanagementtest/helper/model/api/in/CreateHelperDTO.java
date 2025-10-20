// Ubicación: org.cnt.appointmentmanagementtest.helper.model.api.in

package org.cnt.appointmentmanagementtest.helper.model.api.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateHelperDTO {
    private String username;
    private String clearPassword;
    private String name;
    private String phone;
    private String email;
    private List<String> roles;
}
