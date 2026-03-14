package org.cnt.appointmentmanagementtest.helper.service;

import org.cnt.appointmentmanagementtest.appointment.model.api.in.UpdateAppointmentDTO;
import org.cnt.appointmentmanagementtest.appointment.model.api.out.AppointmentCompleteInfoDTO;
import org.cnt.appointmentmanagementtest.appointment.service.AppointmentService;
import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.in.CreateHelperDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Role;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class HelperService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final HelperRepository helperRepository;
    private final AppointmentService appointmentService;

    public HelperService(BCryptPasswordEncoder passwordEncoder, HelperRepository helperRepository, AppointmentService appointmentService) {
        this.passwordEncoder = passwordEncoder;
        this.helperRepository = helperRepository;
        this.appointmentService = appointmentService;
    }

    public HelperProfileDTO getHelperProfile(Helper helper) {
        HelperProfileDTO helperProfileDTO = new HelperProfileDTO();

        helperProfileDTO.setId(helper.getId());
        helperProfileDTO.setUsername(helper.getUsername());
        helperProfileDTO.setName(helper.getName());
        helperProfileDTO.setPhone(helper.getPhone());
        helperProfileDTO.setEmail(helper.getEmail());
        helperProfileDTO.setRole(helper.getRoles().stream().map(Enum::toString).toList());
        helperProfileDTO.setCreatedAt(helper.getCreatedAt());
        helperProfileDTO.setPendingAppointments(appointmentService.getAppointmentsFromHelper(helper.getId(), Pageable.unpaged()));
        return helperProfileDTO;
    }

    public Page<HelperProfileDTO> getAllHelpers(Pageable pageable) {
        Page<Helper> helpers = helperRepository.findAll(pageable);

        return helpers.map(this::getHelperProfile);
    }

    public HelperProfileDTO updateHelperProfile(Helper helper, UpdateHelperProfileDTO updateHelperProfileDTO) {

        if (updateHelperProfileDTO.getEmail() != null) {
            helper.setEmail(updateHelperProfileDTO.getEmail());
        }

        if (updateHelperProfileDTO.getPhone() != null) {
            helper.setPhone(updateHelperProfileDTO.getPhone());
        }

        if (updateHelperProfileDTO.getUsername() != null) {
            helper.setUsername(updateHelperProfileDTO.getUsername());
        }

        // 🔐 Cambio de contraseña seguro
        if (updateHelperProfileDTO.getNewPassword() != null && !updateHelperProfileDTO.getNewPassword().isBlank()) {

            if (updateHelperProfileDTO.getCurrentPassword() == null || updateHelperProfileDTO.getCurrentPassword().isBlank()) {
                throw new IllegalArgumentException("Debes proporcionar la contraseña actual.");
            }

            boolean matches = passwordEncoder.matches(
                    updateHelperProfileDTO.getCurrentPassword(),
                    helper.getPasswordHashed()
            );

            if (!matches) {
                throw new IllegalArgumentException("La contraseña actual no es correcta.");
            }

            helper.setPasswordHashed(
                    passwordEncoder.encode(updateHelperProfileDTO.getNewPassword())
            );
        }

        return getHelperProfile(helperRepository.save(helper));
    }

    public Helper save(CreateHelperDTO dto) {
        Helper helper = new Helper();

        helper.setUsername(dto.getUsername());
        helper.setPasswordHashed(passwordEncoder.encode(dto.getClearPassword()));
        helper.setName(dto.getName());
        helper.setPhone(dto.getPhone());
        helper.setEmail(dto.getEmail());
        return helperRepository.save(helper);
    }

    public Helper delete(UUID id) {
        Helper helper = helperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Helper with id " + id + " not found"));

        Helper system = helperRepository.findFirstHelperByRole(Role.SYSTEM).orElseThrow();

        appointmentService.getAppointmentsFromHelper(helper.getId(), Pageable.unpaged())
                .forEach(appointment -> appointmentService.updateAppointment(appointment.getId(), new UpdateAppointmentDTO(null, null, null, null, system.getId())));

        helperRepository.deleteById(id);

        return helper;
    }
}
