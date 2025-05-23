package org.cnt.appointmentmanagementtest.common.configs;

import org.cnt.appointmentmanagementtest.appointment.service.AppointmentWorkflowService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class AppScheduledConfig {


    private final AppointmentWorkflowService appointmentWorkflowService;

    public AppScheduledConfig(AppointmentWorkflowService appointmentWorkflowService) {
        this.appointmentWorkflowService = appointmentWorkflowService;
    }

    @Scheduled(cron = "59 59 03 L * *")
    public void weeklyAppointmentArchiving() {
        int appointments = appointmentWorkflowService.archiveAppointments();

        System.out.println(appointments + " appointments archived");
    }

    @Scheduled(cron = "00 00 10 * * 1-5")
    public void dailyAppointmentReminder() {
        int appointments = appointmentWorkflowService.sendReminders();

        System.out.println(appointments + " reminders sent");
    }
}
