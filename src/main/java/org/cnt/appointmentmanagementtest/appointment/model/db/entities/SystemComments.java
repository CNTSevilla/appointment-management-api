package org.cnt.appointmentmanagementtest.appointment.model.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SystemComments {

    CREATE("test", "Se ha agendado la cita");

    private final String code;
    private final String comment;


}
