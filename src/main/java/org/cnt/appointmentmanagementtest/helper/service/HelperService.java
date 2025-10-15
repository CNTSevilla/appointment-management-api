package org.cnt.appointmentmanagementtest.helper.service;

import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List; // ✅ Import necesario
import java.util.stream.Collectors;
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

        helperProfileDTO.setUsername(helper.getUsername());
        helperProfileDTO.setName(helper.getName());
        helperProfileDTO.setPhone(helper.getPhone());
        helperProfileDTO.setEmail(helper.getEmail());
        helperProfileDTO.setRole(helper.getRoles().stream().map(Enum::toString).toList());
        helperProfileDTO.setCreatedAt(helper.getCreatedAt()); // ✅ Asignar el nuevo campo
        return helperProfileDTO;
    }


    public Page<HelperProfileDTO> getAllHelpers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
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
}
