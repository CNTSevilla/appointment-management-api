package org.cnt.appointmentmanagementtest.helper.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.service.AuthenticationService;
import org.cnt.appointmentmanagementtest.helper.service.HelperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/helper")
public class HelperController {

    private final AuthenticationService authenticationService;
    private final HelperService helperService;

    public HelperController(AuthenticationService authenticationService, HelperService helperService) {
        this.authenticationService = authenticationService;
        this.helperService = helperService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        Helper helper = authenticationService.getAuthenticatedHelper(request);

        HelperProfileDTO helperProfileDTO = helperService.getHelperProfile(helper);

        return ResponseEntity.ok(helperProfileDTO);
    }

    @PutMapping()
    public ResponseEntity<?> updateHelperProfile(HttpServletRequest request, UpdateHelperProfileDTO updateHelperProfileDTO) {
        Helper helper = authenticationService.getAuthenticatedHelper(request);

        HelperProfileDTO helperProfileDTO = helperService.updateHelperProfile(helper, updateHelperProfileDTO);

        return ResponseEntity.ok(helperProfileDTO);
    }
}
