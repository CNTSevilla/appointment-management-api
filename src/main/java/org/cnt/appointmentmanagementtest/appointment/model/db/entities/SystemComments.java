package org.cnt.appointmentmanagementtest.appointment.model.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SystemComments {

    CREATE("test", "Se ha agendado la cita"),
    DELETE("delete", "Se fue a la puta"),
    UPDATE("actualisao", "pos es lo mismo pero no");

    private final String code;
    private final String comment;


}
