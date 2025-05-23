package org.cnt.appointmentmanagementtest.person_in_need.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.out.PersonInNeedCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.repositories.PersonInNeedRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonInNeedService {

    PersonInNeedRepository personInNeedRepository;

    public PersonInNeedService(PersonInNeedRepository personInNeedRepository) {
        this.personInNeedRepository = personInNeedRepository;
    }

    public PersonInNeed getOnePersonInNeed(UUID id) {
        //TODO: Necesario obtener el primer get
        return null;
    }

    public PersonInNeedCompleteInfoDTO getOnePersonInNeedWithAppointments(UUID id) {
        PersonInNeed personInNeed = personInNeedRepository.findById(id).get();

        PersonInNeedCompleteInfoDTO completeInfoDTO = new PersonInNeedCompleteInfoDTO();
        completeInfoDTO.setName(personInNeed.getName());
        completeInfoDTO.setEmail(personInNeed.getEmail());
        completeInfoDTO.setPhone(personInNeed.getPhone());

        completeInfoDTO.setAppointments(
                personInNeed.getAppointments()
                        .stream()
                        .map(app -> new AppointmentCompleteInfoDTO(
                                app.getId(),
                                app.getDateTime(),
                                app.getPriority(),
                                app.getStatus(),
                                app.getPersonInNeed().getName(),
                                app.getHelper().getName(),
                                app.getComments().stream().map(comm -> new GetCommentDTO(
                                        comm.getId(),
                                        comm.getComment(),
                                        comm.getDate(),
                                        comm.getHelper().getId())).toList())).toList());

        return completeInfoDTO;
    }

    public PersonInNeed save(CreatePersonInNeedDTO dto) {
        PersonInNeed personInNeed = new PersonInNeed();
        personInNeed.setName(dto.getName());
        personInNeed.setEmail(dto.getEmail());
        personInNeed.setPhone(dto.getPhone());

        return personInNeedRepository.save(personInNeed);
    }

    public PersonInNeed update(UUID id, CreatePersonInNeedDTO dto) {
        // TODO: Necesario terminar el update
        return null;
    }

    public PersonInNeed delete(UUID id) {

        // TODO: Necesario terminar el delete.
        return null;
    }
}
