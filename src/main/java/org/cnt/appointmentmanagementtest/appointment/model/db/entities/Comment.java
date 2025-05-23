package org.cnt.appointmentmanagementtest.appointment.model.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private UUID id;

    private String comment;
    private ZonedDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "helper_id")
    private Helper helper;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

}
