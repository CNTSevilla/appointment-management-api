package org.cnt.appointmentmanagementtest.appointment.controllers;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentBasicInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateAppointmentDTO;
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

}