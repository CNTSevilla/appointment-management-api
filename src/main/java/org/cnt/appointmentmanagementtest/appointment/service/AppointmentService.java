package org.cnt.appointmentmanagementtest.appointment.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentBasicInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentStatisticsDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Appointment;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Status;
import org.cnt.appointmentmanagementtest.appointment.model.db.repositories.AppointmentRepository;
import org.cnt.appointmentmanagementtest.appointment.model.db.repositories.CommentRepository;
import org.cnt.appointmentmanagementtest.appointment.model.db.entities.SystemComments;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.repositories.PersonInNeedRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final PersonInNeedRepository personInNeedRepository;
    private final HelperRepository helperRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PersonInNeedRepository personInNeedRepository, HelperRepository helperRepository, CommentRepository commentRepository, CommentService commentService) {
        this.appointmentRepository = appointmentRepository;
        this.personInNeedRepository = personInNeedRepository;
        this.helperRepository = helperRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    public AppointmentBasicInfoDTO createAppointment(CreateAppointmentDTO dto) {
        PersonInNeed personInNeed = personInNeedRepository.findById(dto.getPersonInNeed()).get();
        Helper helper = helperRepository.findById(dto.getHelper()).get();

        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setPriority(dto.getPriority());
        appointment.setStatus(dto.getStatus());
        appointment.setPersonInNeed(personInNeed);
        appointment.setHelper(helper);
        personInNeed.getAppointments().add(appointment);
        helper.addAppointment(appointment);

        Appointment createdAppointment = appointmentRepository.save(appointment);

        commentService.createSystemComment(createdAppointment.getId(), SystemComments.CREATE);

        if (dto.getInitialComment() != null ) {
            if (!dto.getInitialComment().isEmpty() && !dto.getInitialComment().isBlank()) {
                CreateCommentDTO commentDto = new CreateCommentDTO();
                commentDto.setAppointmentId(createdAppointment.getId());
                commentDto.setHelper(dto.getFirstCommentId());
                commentDto.setComment(dto.getInitialComment());

                commentService.createComment(commentDto);
            }
        }

        return getAppointmentBasicInfoDTO(createdAppointment);

    }

    private static AppointmentBasicInfoDTO getAppointmentBasicInfoDTO(Appointment createdAppointment) {
        AppointmentBasicInfoDTO createdAppointmentDTO = new AppointmentBasicInfoDTO();
        createdAppointmentDTO.setId(createdAppointment.getId());
        createdAppointmentDTO.setDateTime(createdAppointment.getDateTime());
        createdAppointmentDTO.setStatus(createdAppointment.getStatus());
        createdAppointmentDTO.setPriority(createdAppointment.getPriority());
        createdAppointmentDTO.setPersonInNeed(createdAppointment.getPersonInNeed().getName());
        createdAppointmentDTO.setHelper(createdAppointment.getHelper().getName());
        return createdAppointmentDTO;
    }

    public List<AppointmentCompleteInfoDTO> getAppointmentsFromHelper(UUID helperId, Pageable pageable) {
        List<AppointmentCompleteInfoDTO> dtos = new ArrayList<>();
        appointmentRepository.findAppointmentsByHelperId(helperId, pageable)
                .stream()
                .filter(appointment -> appointment.getStatus().equals(Status.ACTIVE) || appointment.getStatus().equals(Status.DEFERRED))
                .forEach(
                appointment ->
                        dtos.add(new AppointmentCompleteInfoDTO(appointment.getId(),
                                                        appointment.getDateTime(), appointment.getPriority(),
                                                        appointment.getStatus(), appointment.getPersonInNeed().getName(),
                                                        appointment.getHelper().getName(), null))
        );

        return dtos;
    }

    public List<AppointmentBasicInfoDTO> getAppointments(Pageable pageable) {
           Page<Appointment> appointments = appointmentRepository.findAll(pageable);

           List<AppointmentBasicInfoDTO> dtos = new ArrayList<>();

           appointments.forEach(appointment -> {
               dtos.add(new AppointmentBasicInfoDTO(appointment.getId(),
                                                    appointment.getDateTime(),
                                                    appointment.getPriority(),
                                                    appointment.getStatus(),
                                                    appointment.getPersonInNeed().getName(),
                                                    appointment.getHelper().getName()));
           });

           return dtos;
    }

    public AppointmentCompleteInfoDTO getAppointment(UUID uuid) {
        Appointment appointment = appointmentRepository.findById(uuid).get();

        AppointmentCompleteInfoDTO completeInfoDTO = new AppointmentCompleteInfoDTO();
        completeInfoDTO.setId(appointment.getId());
        completeInfoDTO.setDateTime(appointment.getDateTime());
        completeInfoDTO.setStatus(appointment.getStatus());
        completeInfoDTO.setPriority(appointment.getPriority());
        completeInfoDTO.setPersonInNeed(appointment.getPersonInNeed().getName());
        completeInfoDTO.setHelper(appointment.getHelper().getName());
        completeInfoDTO.setComments(commentRepository.findCommentsByAppointment_Id(appointment.getId())
                .stream()
                .map(comment ->
                        new GetCommentDTO(comment.getId(), comment.getComment(), comment.getDate(), comment.getHelper().getId(), comment.getHelper().getName()))
                .toList());

        return completeInfoDTO;
    }

    public List<AppointmentCompleteInfoDTO> getAppointmentsWithComments(Pageable pageable, ZonedDateTime start, ZonedDateTime end) {
        Page<Appointment> appointments = appointmentRepository.findAppointmentsByDateTimeBetween(start, end, pageable);

        List<AppointmentCompleteInfoDTO> dtos = new ArrayList<>();

        appointments.forEach(appointment -> {
            AppointmentCompleteInfoDTO appDTO = new AppointmentCompleteInfoDTO();

            appDTO.setId(appointment.getId());
            appDTO.setDateTime(appointment.getDateTime());
            appDTO.setStatus(appointment.getStatus());
            appDTO.setPriority(appointment.getPriority());
            appDTO.setPersonInNeed(appointment.getPersonInNeed().getName());
            appDTO.setHelper(appointment.getHelper().getName());
            appDTO.setComments(commentRepository.findCommentsByAppointment_Id(appointment.getId())
                    .stream()
                    .map(comment ->
                            new GetCommentDTO(comment.getId(), comment.getComment(), comment.getDate(), comment.getHelper().getId(), comment.getHelper().getName()))
                    .toList());
            dtos.add(appDTO);
        });

        return dtos;
    }

    public AppointmentBasicInfoDTO updateAppointment(UUID id, UpdateAppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (dto.getDateTime() != null) {
            appointment.setDateTime(dto.getDateTime());
        }
        if (dto.getPriority() != null) {
            appointment.setPriority(dto.getPriority());
        }
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
        if (dto.getPersonInNeed() != null) {
            PersonInNeed personInNeed = personInNeedRepository.findById(dto.getPersonInNeed())
                    .orElseThrow(() -> new RuntimeException("PersonInNeed not found"));
            appointment.getPersonInNeed().getAppointments().remove(appointment);
            appointment.setPersonInNeed(personInNeed);
            personInNeed.getAppointments().add(appointment);
        }
        if (dto.getHelper() != null) {
            Helper helper = helperRepository.findById(dto.getHelper())
                    .orElseThrow(() -> new RuntimeException("Helper not found"));
            appointment.getHelper().getAppointment().remove(appointment);
            appointment.setHelper(helper);
            helper.addAppointment(appointment);
        }

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        commentService.createSystemComment(updatedAppointment.getId(), SystemComments.UPDATE);

        return getAppointmentBasicInfoDTO(updatedAppointment);
    }

    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        commentService.createSystemComment(appointment.getId(), SystemComments.DELETE);

        appointmentRepository.delete(appointment);
    }

    // ! DA ERROR, NECESIO QUE DEVEULVA UN Page NO UN List | Como en getAllHelpers
    public List<AppointmentCompleteInfoDTO> getAllAppointmentsByStatus(Pageable pageable, String status) {

        List<Appointment> appointments = appointmentRepository.findAppointmentsByStatus(Status.valueOf(status.toUpperCase()), pageable);

        List<AppointmentCompleteInfoDTO> dtos = new ArrayList<>();
        appointments.forEach(appointment -> {
            AppointmentCompleteInfoDTO completeInfoDTO = new AppointmentCompleteInfoDTO();
            completeInfoDTO.setId(appointment.getId());
            completeInfoDTO.setDateTime(appointment.getDateTime());
            completeInfoDTO.setStatus(appointment.getStatus());
            completeInfoDTO.setPriority(appointment.getPriority());
            completeInfoDTO.setPersonInNeed(appointment.getPersonInNeed().getName());
            completeInfoDTO.setHelper(appointment.getHelper().getName());
            completeInfoDTO.setComments(commentRepository.findCommentsByAppointment_Id(appointment.getId())
                    .stream()
                    .map(comment ->
                            new GetCommentDTO(comment.getId(), comment.getComment(), comment.getDate(), comment.getHelper().getId(), comment.getHelper().getName()))
                    .toList());
            dtos.add(completeInfoDTO);
        });

        return dtos;
    }

    public AppointmentStatisticsDTO getAppointmentStatistics(UUID helperId) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime startOfDay = ZonedDateTime.now(zoneId).toLocalDate().atStartOfDay(zoneId);
        ZonedDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        Status pendingStatus = Status.ACTIVE;

        int todayAppointments = Math.toIntExact(
                appointmentRepository.countByDateTimeBetween(startOfDay, endOfDay)
        );

        int yourTodayAppointments = Math.toIntExact(
                appointmentRepository.countByHelperIdAndDateTimeBetween(helperId, startOfDay, endOfDay)
        );

        int deferredAppointments = Math.toIntExact(
                appointmentRepository.countByStatus(Status.DEFERRED)
        );

        int yourDeferredAppointments = Math.toIntExact(
                appointmentRepository.countByHelperIdAndStatus(helperId, Status.DEFERRED)
        );

        int completedAppointments = Math.toIntExact(
                appointmentRepository.countByStatus(Status.COMPLETED)
        );

        int yourCompletedAppointments = Math.toIntExact(
                appointmentRepository.countByHelperIdAndStatus(helperId, Status.COMPLETED)
        );

        int pendingAppointments = Math.toIntExact(
                appointmentRepository.countByStatus(pendingStatus)
        );

        int yourPendingAppointments = Math.toIntExact(
                appointmentRepository.countByHelperIdAndStatus(helperId, pendingStatus)
        );

        int archivedAppointments = Math.toIntExact(
                appointmentRepository.countByStatus(Status.ARCHIVED)
        );

        int yourArchivedAppointments = Math.toIntExact(
                appointmentRepository.countByHelperIdAndStatus(helperId, Status.ARCHIVED)
        );

        int yourTotalAppointments = Math.toIntExact(
                appointmentRepository.countByHelperId(helperId)
        );

        int totalAppointments = Math.toIntExact(
                appointmentRepository.count()
        );

        return new AppointmentStatisticsDTO(
                todayAppointments,
                yourTodayAppointments,
                yourDeferredAppointments,
                deferredAppointments,
                yourCompletedAppointments,
                completedAppointments,
                yourPendingAppointments,
                pendingAppointments,
                yourArchivedAppointments,
                archivedAppointments,
                yourTotalAppointments,
                totalAppointments
        );
    }

    public long countAppointmentsByStatus(Status status) {
        return appointmentRepository.countByStatus(status);
    }
}
