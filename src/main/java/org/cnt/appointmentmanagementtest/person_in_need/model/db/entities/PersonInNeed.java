package org.cnt.appointmentmanagementtest.person_in_need.model.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInNeed {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String phone;
    private String email;

    @CreationTimestamp
    private Instant createdAt;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "personInNeed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

}
