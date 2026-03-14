package org.cnt.appointmentmanagementtest.appointment.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.AppointmentFilterDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentBasicInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.service.AppointmentService;
import org.cnt.appointmentmanagementtest.common.annotations.DefaultPageable;
import org.cnt.appointmentmanagementtest.common.mail.CustomEmailService;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.service.AuthenticationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final CustomEmailService customEmailService;
    private final AppointmentService appointmentService;
    private final AuthenticationService authenticationService;

    public AppointmentController(AppointmentService appointmentService, CustomEmailService customEmailService,
                                 AuthenticationService authenticationService) {
        this.appointmentService = appointmentService;
        this.customEmailService = customEmailService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentBasicInfoDTO>> getAllAppointments(@DefaultPageable Pageable pageable) throws Exception {
        return ResponseEntity.ok(appointmentService.getAppointments(pageable));
    }

    @GetMapping("/all-with-comments")
    public ResponseEntity<List<AppointmentCompleteInfoDTO>> getAllAppointmentsWithComments(@DefaultPageable Pageable pageable, @NotNull @RequestBody AppointmentFilterDTO filter) throws Exception {

        if (filter.getEnd() == null) {
            filter.setEnd(ZonedDateTime.now());
        }
        return ResponseEntity.ok(appointmentService.getAppointmentsWithComments(pageable, filter.getStart(), filter.getEnd()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentCompleteInfoDTO> getAppointmentById(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @PostMapping("")
    public ResponseEntity<AppointmentBasicInfoDTO> createAppointment(@RequestBody CreateAppointmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentBasicInfoDTO> updateAppointment(@PathVariable("id") UUID id, @RequestBody UpdateAppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{status}")
    public ResponseEntity<?> getAllAppointmentsByStatus(@PathVariable("status") String status, @DefaultPageable Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByStatus(pageable, status));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(HttpServletRequest request) {
        Helper helper = authenticationService.getAuthenticatedHelper(request);
        return ResponseEntity.ok(appointmentService.getAppointmentStatistics(helper.getId()));
    }

}