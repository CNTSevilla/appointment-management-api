package org.cnt.appointmentmanagementtest.appointment.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Comment;
import org.cnt.appointmentmanagementtest.appointment.model.db.repositories.AppointmentRepository;
import org.cnt.appointmentmanagementtest.appointment.model.db.repositories.CommentRepository;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.SystemComments;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Role;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;



@Service
public class CommentService {


    private final AppointmentRepository appointmentRepository;
    private final HelperRepository helperRepository;
    private final CommentRepository commentRepository;

    private final static String SYSTEM_PREFIX = "[SISTEMA] ";

    public CommentService(AppointmentRepository appointmentRepository, HelperRepository helperRepository, CommentRepository commentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.helperRepository = helperRepository;
        this.commentRepository = commentRepository;
    }

    public List<GetCommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> {
                    GetCommentDTO dto = new GetCommentDTO();
                    dto.setId(comment.getId());
                    dto.setComment(comment.getComment());
                    dto.setDate(comment.getDate());
                    dto.setHelper(comment.getHelper().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<GetCommentDTO> getCommentsByAppointmentId(UUID appointmentId) {
        return commentRepository.findCommentsByAppointment_Id(appointmentId)
                .stream()
                .map(comment -> {
                    GetCommentDTO dto = new GetCommentDTO();
                    dto.setId(comment.getId());
                    dto.setComment(comment.getComment());
                    dto.setDate(comment.getDate());
                    dto.setHelper(comment.getHelper().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public AppointmentCompleteInfoDTO createComment(CreateCommentDTO dto) {

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).get();
        Helper helper = helperRepository.findById(dto.getHelper()).get();

        Comment comment = new Comment();
        comment.setComment(dto.getComment());
        comment.setDate(ZonedDateTime.now());
        comment.setAppointment(appointment);
        comment.setHelper(helper);

        Comment commentToSave = commentRepository.save(comment);
        helperRepository.save(helper);
        appointmentRepository.save(appointment);

        AppointmentCompleteInfoDTO completeInfoDTO = new AppointmentCompleteInfoDTO();
        completeInfoDTO.setId(appointment.getId());
        completeInfoDTO.setDateTime(appointment.getDateTime());
        completeInfoDTO.setStatus(appointment.getStatus());
        completeInfoDTO.setPriority(appointment.getPriority());
        completeInfoDTO.setPersonInNeed(appointment.getPersonInNeed().getName());
        completeInfoDTO.setHelper(appointment.getHelper().getName());

        GetCommentDTO commentDTO = new GetCommentDTO();
        commentDTO.setId(commentToSave.getId());
        commentDTO.setDate(commentToSave.getDate());
        commentDTO.setComment(commentToSave.getComment());
        commentDTO.setHelper(comment.getHelper().getId());
        completeInfoDTO.setComments(List.of(commentDTO));

        return completeInfoDTO;
    }

    public void createSystemComment(UUID appointmentId, SystemComments sysComments) {

        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        Helper helper = helperRepository.findFirstHelperByRole(Role.SYSTEM).get();

        Comment comment = new Comment();
        comment.setComment(SYSTEM_PREFIX + sysComments.getComment());
        comment.setDate(ZonedDateTime.now());
        comment.setAppointment(appointment);
        comment.setHelper(helper);

        commentRepository.save(comment);
    }

    public GetCommentDTO getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        GetCommentDTO dto = new GetCommentDTO();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setDate(comment.getDate());
        dto.setHelper(comment.getHelper().getId());
        return dto;
    }

    public GetCommentDTO updateComment(UUID id, UpdateCommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Solo actualizar el comentario si viene en el DTO (no null)
        if (dto.getComment() != null) {
            comment.setComment(dto.getComment());
        }

        Comment updatedComment = commentRepository.save(comment);

        GetCommentDTO responseDto = new GetCommentDTO();
        responseDto.setId(updatedComment.getId());
        responseDto.setComment(updatedComment.getComment());
        responseDto.setDate(updatedComment.getDate());
        responseDto.setHelper(updatedComment.getHelper().getId());
        return responseDto;
    }

    public void deleteComment(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }


}
