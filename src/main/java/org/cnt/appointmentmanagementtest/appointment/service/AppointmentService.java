package org.cnt.appointmentmanagementtest.appointment.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentBasicInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.CreateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<AppointmentBasicInfoDTO> getAppointments() {
           List<Appointment> appointments = appointmentRepository.findAll();

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
                        new GetCommentDTO(comment.getId(), comment.getComment(), comment.getDate(), comment.getHelper().getId()))
                .toList());

        return completeInfoDTO;
    }

    public AppointmentBasicInfoDTO updateAppointment(UUID id, UpdateAppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Solo actualizar los campos que vienen en el DTO (no null)
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


    public List<AppointmentCompleteInfoDTO> getAllAppointmentsByStatus(int page, int size, String sortField, String sortDirection, String status) {
        Sort.Direction direction = (sortDirection.equals("desc") || sortDirection.equals("DESC"))
                                            ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
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
                            new GetCommentDTO(comment.getId(), comment.getComment(), comment.getDate(), comment.getHelper().getId()))
                    .toList());
            dtos.add(completeInfoDTO);
        });

        return dtos;
    }
}
