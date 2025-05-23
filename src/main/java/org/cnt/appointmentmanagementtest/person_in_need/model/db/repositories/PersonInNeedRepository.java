package org.cnt.appointmentmanagementtest.person_in_need.model.db.repositories;

import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonInNeedRepository extends JpaRepository<PersonInNeed, UUID> {
}
