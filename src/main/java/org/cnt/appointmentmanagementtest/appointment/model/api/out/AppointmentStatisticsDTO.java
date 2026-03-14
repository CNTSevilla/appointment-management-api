package org.cnt.appointmentmanagementtest.appointment.model.api.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentStatisticsDTO {
    private int todayAppointments;
    private int yourTodayAppointments;
    private int yourDeferredAppointments;
    private int deferredAppointments;
    private int yourCompletedAppointments;
    private int completedAppointments;
    private int yourPendingAppointments;
    private int pendingAppointments;
    private int yourArchivedAppointments;
    private int archivedAppointments;
    private int yourTotalAppointments;
    private int totalAppointments;
}
