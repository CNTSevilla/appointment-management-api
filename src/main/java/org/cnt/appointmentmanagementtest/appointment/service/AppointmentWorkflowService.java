package org.cnt.appointmentmanagementtest.appointment.service;

import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Status;
import org.cnt.appointmentmanagementtest.appointment.model.db.repositories.AppointmentRepository;
import org.cnt.appointmentmanagementtest.common.mail.CustomEmailService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AppointmentWorkflowService {

    private final AppointmentRepository appointmentRepository;
    private final CustomEmailService customEmailService;

    public AppointmentWorkflowService(AppointmentRepository appointmentRepository, CustomEmailService customEmailService) {
        this.appointmentRepository = appointmentRepository;
        this.customEmailService = customEmailService;
    }

    public int archiveAppointments() {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByStatus(Status.COMPLETED);

        appointments.forEach(appointment -> appointment.setStatus(Status.ARCHIVED));
        appointmentRepository.saveAll(appointments);

        return appointments.size();
    }

    public int sendReminders() {
        ZonedDateTime startOfToday = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        ZonedDateTime endOfToday = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59);

        List<Appointment> appointments = appointmentRepository.findAppointmentsByStatus(Status.ACTIVE)
                .stream().filter(appointment -> appointment.getDateTime().isBefore(endOfToday)
                        && appointment.getDateTime().isAfter(startOfToday)).toList();

        for (Appointment appointment : appointments) {
            customEmailService.sendReminderToPersonInNeed(appointment.getPersonInNeed().getEmail(),
                    appointment.getPersonInNeed().getName(), appointment.getHelper().getName(), appointment.getDateTime());

            customEmailService.sendReminderToHelper(appointment.getHelper().getEmail(),
                    appointment.getPersonInNeed().getName(), appointment.getHelper().getName(), appointment.getDateTime());
        }

        return appointments.size();
    }
}
