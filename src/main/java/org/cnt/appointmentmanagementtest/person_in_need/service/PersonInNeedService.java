package org.cnt.appointmentmanagementtest.person_in_need.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.GetCommentDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.out.PersonInNeedCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.out.PersonInNeedSimpleDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.repositories.PersonInNeedRepository;
import org.springframework.data.domain.Sort;
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

    public Page<PersonInNeedSimpleDTO> getAllPersonInNeed(Pageable pageable) {

        Page<PersonInNeed> people = personInNeedRepository.findAll(pageable);

        return people
                .map(person ->
                        new PersonInNeedSimpleDTO(
                            person.getName(),
                            person.getPhone(),
                            person.getEmail(),
                            person.getCreatedAt()
                        )
                );
    }

    public PersonInNeedSimpleDTO getOnePersonInNeed(UUID id) {
        PersonInNeed personInNeed = personInNeedRepository.findById(id).get();

        PersonInNeedSimpleDTO pinDTO = new PersonInNeedSimpleDTO();
        pinDTO.setName(personInNeed.getName());
        pinDTO.setEmail(personInNeed.getEmail());
        pinDTO.setPhone(personInNeed.getPhone());
        pinDTO.setCreatedAt(personInNeed.getCreatedAt());
        return pinDTO;
    }

    public PersonInNeedCompleteInfoDTO getOnePersonInNeedWithAppointments(UUID id) {
        PersonInNeed personInNeed = personInNeedRepository.findById(id).get();

        PersonInNeedCompleteInfoDTO completeInfoDTO = new PersonInNeedCompleteInfoDTO();
        completeInfoDTO.setName(personInNeed.getName());
        completeInfoDTO.setEmail(personInNeed.getEmail());
        completeInfoDTO.setPhone(personInNeed.getPhone());
        completeInfoDTO.setCreatedAt(personInNeed.getCreatedAt());

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
                                        comm.getHelper().getId(),
                                        comm.getHelper().getName()
                                        )).toList())).toList());

        return completeInfoDTO;
    }

    public void save(CreatePersonInNeedDTO dto) {
        PersonInNeed personInNeed = new PersonInNeed();
        personInNeed.setName(dto.getName());
        personInNeed.setEmail(dto.getEmail());
        personInNeed.setPhone(dto.getPhone());

        personInNeedRepository.save(personInNeed);
    }

    public void update(UUID id, CreatePersonInNeedDTO dto) {
        Optional<PersonInNeed> person = personInNeedRepository.findById(id);

        if (person.isPresent()) {
            PersonInNeed personInNeed = new PersonInNeed();
            personInNeed.setId(id);
            personInNeed.setName(dto.getName() != null ? dto.getName() : personInNeed.getName());
            personInNeed.setEmail(dto.getEmail() != null ? dto.getEmail() : personInNeed.getEmail());
            personInNeed.setPhone(dto.getPhone() != null ? dto.getPhone() : personInNeed.getPhone());

            personInNeed.setAppointments(personInNeed.getAppointments());

            personInNeedRepository.save(personInNeed);
        }
        throw new IllegalArgumentException("PersonInNeed with id " + id + " not found");

    }

    public void delete(UUID id) {
        personInNeedRepository.deleteById(id);

    }
}
