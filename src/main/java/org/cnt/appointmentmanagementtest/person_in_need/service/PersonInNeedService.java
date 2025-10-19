package org.cnt.appointmentmanagementtest.person_in_need.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.out.PersonInNeedCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.repositories.PersonInNeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonInNeedService {

    PersonInNeedRepository personInNeedRepository;

    public PersonInNeedService(PersonInNeedRepository personInNeedRepository) {
        this.personInNeedRepository = personInNeedRepository;
    }

    public Page<PersonInNeed> getAllPersonInNeed(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personInNeedRepository.findAll(pageable);
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
        Optional<PersonInNeed> person = personInNeedRepository.findById(id);

        if (person.isPresent()) {
            PersonInNeed personInNeed = new PersonInNeed();
            personInNeed.setId(id);
            personInNeed.setName(dto.getName() != null ? dto.getName() : personInNeed.getName());
            personInNeed.setEmail(dto.getEmail() != null ? dto.getEmail() : personInNeed.getEmail());
            personInNeed.setPhone(dto.getPhone() != null ? dto.getPhone() : personInNeed.getPhone());

            personInNeed.setAppointments(personInNeed.getAppointments());

            return personInNeedRepository.save(personInNeed);
        }
        throw new IllegalArgumentException("PersonInNeed with id " + id + " not found");

    }

    public PersonInNeed delete(UUID id) {
        PersonInNeed person = personInNeedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "PersonInNeed with id " + id + " not found"));
        personInNeedRepository.deleteById(id);

        return person;
    }
}
