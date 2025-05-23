package org.cnt.appointmentmanagementtest.person_in_need.controllers;

import org.cnt.appointmentmanagementtest.person_in_need.model.api.in.CreatePersonInNeedDTO;
import org.cnt.appointmentmanagementtest.person_in_need.model.db.entities.PersonInNeed;
import org.cnt.appointmentmanagementtest.person_in_need.service.PersonInNeedService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonInNeedController.class)
class PersonInNeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInNeedService personInNeedService;

    private UUID testId;
    private PersonInNeed mockPersonInNeed;
    private CreatePersonInNeedDTO mockRequest;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

        mockPersonInNeed = new PersonInNeed();
        mockPersonInNeed.setId(testId);
        mockPersonInNeed.setName("John Doe");

        mockRequest = new CreatePersonInNeedDTO();
        mockRequest.setName("John Doe");
    }

    @Test
    void testGetPersonInNeedById() throws Exception {
        // Simulando comportamiento del servicio
        when(personInNeedService.getOnePersonInNeed(testId)).thenReturn(mockPersonInNeed);

        // Realiza una solicitud GET y valida la respuesta
        mockMvc.perform(get("/api/v1/person_in_need/{id}", String.valueOf(testId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));

        // Verificar que el servicio fue llamado una vez
        verify(personInNeedService, times(1)).getOnePersonInNeed(testId);
    }

    @Test
    void testCreatePersonInNeed() throws Exception {
        // Simulando comportamiento del servicio
        when(personInNeedService.save(any(CreatePersonInNeedDTO.class))).thenReturn(mockPersonInNeed);

        // Realiza una solicitud POST y valida la respuesta
        mockMvc.perform(post("/api/v1/person_in_need")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "age": 30
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));

        // Verificar que el servicio fue llamado una vez
        verify(personInNeedService, times(1)).save(any(CreatePersonInNeedDTO.class));
    }

    @Test
    void testUpdatePersonInNeed() throws Exception {
        // Simulando comportamiento del servicio
        when(personInNeedService.update(eq(testId), any(CreatePersonInNeedDTO.class))).thenReturn(mockPersonInNeed);

        // Realiza una solicitud PUT y valida la respuesta
        mockMvc.perform(put("/api/v1/person_in_need/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "age": 30
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));

        // Verificar que el servicio fue llamado una vez
        verify(personInNeedService, times(1)).update(eq(testId), any(CreatePersonInNeedDTO.class));
    }

    @Test
    void testDeletePersonInNeed() throws Exception {
        // Simulando comportamiento del servicio
        when(personInNeedService.delete(testId)).thenReturn(mockPersonInNeed);

        // Realiza una solicitud DELETE y valida la respuesta
        mockMvc.perform(delete("/api/v1/person_in_need/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));

        // Verificar que el servicio fue llamado una vez
        verify(personInNeedService, times(1)).delete(testId);
    }
}