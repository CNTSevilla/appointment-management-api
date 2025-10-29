package org.cnt.appointmentmanagementtest.helper.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.cnt.appointmentmanagementtest.helper.model.api.in.UpdateHelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.HelperProfileDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.in.CreateHelperDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.service.AuthenticationService;
import org.cnt.appointmentmanagementtest.helper.service.HelperService;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.hibernate.query.SortDirection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/all")
    public ResponseEntity<?> getAllHelpers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        if (!sortDirection.equals("desc") && !sortDirection.equals("DESC")
            && !sortDirection.equals("ASC") && !sortDirection.equals("asc")) {
            return ResponseEntity.badRequest().body("sortDirection must be 'asc' or 'desc'.");
        }
        return ResponseEntity.ok(helperService.getAllHelpers(page, size, sortField, sortDirection));

    }

    // @DeleteMapping("{id}")
    // public ResponseEntity<Helper> createPersonInNeed(@PathVariable("id") UUID id) {
    //     return ResponseEntity.status(HttpStatus.OK).body(helperService.delete(id));
    // }


    @PostMapping()
    public ResponseEntity<Helper> createHelper(@RequestBody CreateHelperDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(helperService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHelper(@PathVariable("id") UUID id) {
        helperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
