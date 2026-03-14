package org.cnt.appointmentmanagementtest.appointment.model.db.repositories;

import org.cnt.appointmentmanagementtest.appointment.model.db.entities.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findCommentsByAppointment_Id(UUID appointmentId);


}
