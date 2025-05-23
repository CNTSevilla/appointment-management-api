package org.cnt.appointmentmanagementtest.helper.service;

import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        return helperProfileDTO;
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
