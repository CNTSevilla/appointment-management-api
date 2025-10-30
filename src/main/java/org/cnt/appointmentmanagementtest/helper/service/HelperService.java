package org.cnt.appointmentmanagementtest.helper.service;

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
import java.util.List; // ✅ Import necesario
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

    public HelperService(BCryptPasswordEncoder passwordEncoder, HelperRepository helperRepository) {
        this.passwordEncoder = passwordEncoder;
        this.helperRepository = helperRepository;
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
        return helperProfileDTO;
    }



    public Page<HelperProfileDTO> getAllHelpers(int page, int size, String sortField, String sortDirection) {
        Sort.Direction direction = (sortDirection.equals("desc") || sortDirection.equals("DESC"))
                                            ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
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

        if (updateHelperProfileDTO.getClearPassword() != null) {
            helper.setPasswordHashed(passwordEncoder.encode(updateHelperProfileDTO.getClearPassword()));
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

        Set<Role> roles;

        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            // ✅ Si no se envían roles, asignar uno por defecto
            roles = Set.of(Role.USER);
        } else {
            // ✅ Validar cada rol recibido
            roles = dto.getRoles().stream()
                .map(role -> {
                    try {
                        return Role.valueOf(role.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Rol inválido: " + role);
                    }
                })
                .collect(Collectors.toSet());
        }

        helper.setRoles(roles);

        return helperRepository.save(helper);
    }

    public Helper delete(UUID id) {
        Helper helper = helperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Helper with id " + id + " not found"));
        helperRepository.deleteById(id);

        return helper;
    }
}
