package org.cnt.appointmentmanagementtest.helper.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDTO {
    private String token;

    private long expiresIn;
}