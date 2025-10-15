package org.cnt.appointmentmanagementtest.helper.model.db.repositories;

import com.zaxxer.hikari.util.FastList;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface HelperRepository extends JpaRepository<Helper, UUID> {

    Optional<Helper> getByUsername(String username);

    @Query("SELECT h FROM Helper h JOIN h.roles r WHERE r = :role ORDER BY h.id ASC")
    Optional<Helper> findFirstHelperByRole(@Param("role") Role role);


}
