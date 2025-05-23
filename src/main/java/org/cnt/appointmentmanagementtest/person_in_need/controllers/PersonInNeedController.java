package org.cnt.appointmentmanagementtest.person_in_need.controllers;

import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.service.PersonInNeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/person_in_need")
public class PersonInNeedController {

    private final PersonInNeedService personInNeedService;

    public PersonInNeedController(PersonInNeedService personInNeedService) {
        this.personInNeedService = personInNeedService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonInNeed> getPersonInNeedById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(personInNeedService.getOnePersonInNeed(id));
    }

    @PostMapping("")
    public ResponseEntity<PersonInNeed> createPersonInNeed(@RequestBody CreatePersonInNeedDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personInNeedService.save(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonInNeed> updatePersonInNeed(@PathVariable("id") UUID id, @RequestBody CreatePersonInNeedDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personInNeedService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PersonInNeed> createPersonInNeed(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(personInNeedService.delete(id));
    }

}
