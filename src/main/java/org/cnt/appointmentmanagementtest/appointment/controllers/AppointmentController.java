package org.cnt.appointmentmanagementtest.appointment.controllers;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentBasicInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.service.AppointmentService;
import org.cnt.appointmentmanagementtest.common.mail.CustomEmailService;
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

    public AppointmentController(AppointmentService appointmentService, CustomEmailService customEmailService) {
        this.appointmentService = appointmentService;
        this.customEmailService = customEmailService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentBasicInfoDTO>> getAllAppointments() throws Exception {
        return ResponseEntity.ok(appointmentService.getAppointments());
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
    public ResponseEntity<?> getAllAppointmentsByStatus(
            @PathVariable("status") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateTime") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection

    ) {
        if (!sortDirection.equals("desc") && !sortDirection.equals("DESC")
            && !sortDirection.equals("ASC") && !sortDirection.equals("asc")) {
            return ResponseEntity.badRequest().body("sortDirection must be 'asc' or 'desc'.");
        }
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByStatus(page, size, sortField, sortDirection, status));
    }

}