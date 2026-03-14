package org.cnt.appointmentmanagementtest.person_in_need.controllers;

import org.cnt.appointmentmanagementtest.common.annotations.DefaultPageable;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.api.out.PersonInNeedSimpleDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.service.PersonInNeedService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/person_in_need")
public class PersonInNeedController {

    private final PersonInNeedService personInNeedService;

    public PersonInNeedController(PersonInNeedService personInNeedService) {
        this.personInNeedService = personInNeedService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPersonInNeed(@DefaultPageable Pageable pageable) {
        return ResponseEntity.ok(personInNeedService.getAllPersonInNeed(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonInNeedById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(personInNeedService.getOnePersonInNeed(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createPersonInNeed(@RequestBody CreatePersonInNeedDTO dto) {
        personInNeedService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonInNeed(@PathVariable("id") UUID id, @RequestBody CreatePersonInNeedDTO dto) {
        personInNeedService.update(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonInNeed(@PathVariable("id") UUID id) {
        personInNeedService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
