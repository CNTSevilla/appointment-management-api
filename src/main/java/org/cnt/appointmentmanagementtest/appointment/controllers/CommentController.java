package org.cnt.appointmentmanagementtest.appointment.controllers;

import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.service.CommentService;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<AppointmentCompleteInfoDTO> createComment(@RequestBody CreateCommentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<GetCommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<GetCommentDTO>> getCommentsByAppointmentId(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(commentService.getCommentsByAppointmentId(appointmentId));
    }
}
