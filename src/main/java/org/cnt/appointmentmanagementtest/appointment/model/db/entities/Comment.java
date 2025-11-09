package org.cnt.appointmentmanagementtest.appointment.model.db.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "helper_id")
    @JsonBackReference(value = "helper-comment")
    private Helper helper;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    @JsonBackReference(value = "appointment-comment")
    private Appointment appointment;
}

