package org.cnt.appointmentmanagementtest.helper.service;

import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List; // ✅ Import necesario
import java.util.UUID;
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


    public Helper delete(UUID id) {
        Helper helper = helperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Helper with id " + id + " not found"));
        helperRepository.deleteById(id);

        return helper;
    }
}
