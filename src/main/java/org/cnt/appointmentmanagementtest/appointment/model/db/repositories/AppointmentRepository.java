package org.cnt.appointmentmanagementtest.appointment.model.db.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAppointmentsByStatus(Status status);
    List<Appointment> findAppointmentsByStatus(Status status, Pageable pageable);
    List<Appointment> findAppointmentsByHelperId(UUID helperId, Pageable pageable);

    Page<Appointment> findAppointmentsByDateTimeBetween(ZonedDateTime start, ZonedDateTime end, Pageable pageable);

    long countByDateTimeBetween(ZonedDateTime start, ZonedDateTime end);
    long countByHelperIdAndDateTimeBetween(UUID helperId, ZonedDateTime start, ZonedDateTime end);
    long countByStatus(Status status);
    long countByHelperIdAndStatus(UUID helperId, Status status);
    long countByHelperId(UUID helperId);

}
