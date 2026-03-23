package org.cnt.appointmentmanagementtest.person_in_need.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInNeedSimpleDTO {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private Instant createdAt;

}
