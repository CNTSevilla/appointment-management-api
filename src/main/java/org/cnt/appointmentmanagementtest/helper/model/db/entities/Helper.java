package org.cnt.appointmentmanagementtest.helper.model.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Helper implements UserDetails, Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String passwordHashed;
    private String name;
    private String phone;
    private String email;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "helper", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Appointment> appointment;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "helper-comment")
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .toList();
    }

    @Override
    public String getPassword() {
        return passwordHashed;
    }

    public void addAppointment(Appointment newAppointment) {
        appointment.add(newAppointment);
    }
}
