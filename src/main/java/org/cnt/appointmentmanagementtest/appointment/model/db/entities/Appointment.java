package org.cnt.appointmentmanagementtest.appointment.model.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Appointment {

    @Id
    @GeneratedValue
    private UUID id;

    private ZonedDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "person_in_need_id")
    @JsonBackReference
    private PersonInNeed personInNeed;

    @ManyToOne
    @JoinColumn(name = "helper_id")
    @JsonBackReference
    private Helper helper;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "appointment-comment")
    private List<Comment> comments;
}
