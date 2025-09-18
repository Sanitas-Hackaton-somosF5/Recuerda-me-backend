package com.sanitas.recuerdame.medications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.repository.MedicationRepository;
import com.sanitas.recuerdame.shared.IntakeSlot;

import com.sanitas.recuerdame.user.entity.User;
import com.sanitas.recuerdame.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class MedicationControllerIntegrationTest {

    private static final String BASE_URL = "/api/v1/medications";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        medicationRepository.deleteAll();
        userRepository.deleteAll();

        testUser = userRepository.save(
                User.builder()
                        .username("Ana")
                        .email("ana@gmail.com")
                        .password("demo123")
                        .build()
        );
    }

    @Test
    @DisplayName("POST /medications - should create a medication successfully")
    void addMedication_ShouldReturnCreated() throws Exception {
        List<IntakeSlot> slots = List.of(IntakeSlot.BREAKFAST);
        MedicationRequest request = createMedicationRequest(
                "Ibuprofeno", "Medicamento para el dolor", "200mg",
                LocalDate.now(), LocalDate.now().plusDays(7), slots
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ibuprofeno"))
                .andExpect(jsonPath("$.dose").value("200mg"))
                .andExpect(jsonPath("$.intakeSlots[0]").value("BREAKFAST"));
    }

    @Test
    @DisplayName("POST /medications - should return 409 if medication already exists")
    void addMedication_WhenAlreadyExists_ShouldReturnConflict() throws Exception {
        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Medicamento para el dolor",
                "600mg",
                LocalDate.of(2025, 9, 18),
                LocalDate.of(2025, 9, 20),
                List.of(IntakeSlot.BREAKFAST)
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Ya existe un tratamiento idéntico registrado"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.path").value(BASE_URL));
    }

    @Test
    @DisplayName("POST /medications - should return 404 if user not found")
    void addMedication_WhenUserNotFound_ShouldReturn404() throws Exception {
        // Eliminar la usuaria "Ana" para que el service no la encuentre
        userRepository.deleteAll();

        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Medicamento para el dolor",
                "200mg",
                LocalDate.of(2025, 9, 18),
                LocalDate.of(2025, 9, 25),
                List.of(IntakeSlot.BREAKFAST)
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value(BASE_URL));
    }

    @Test
    @DisplayName("GET /medications/{id} - should return medication by ID")
    void getMedicationById_ShouldReturnMedication() throws Exception {
        Medication medication = createMedication("Ibuprofeno", "Medicamento para el dolor", "200mg",
                LocalDate.now(), LocalDate.now().plusDays(7), List.of(IntakeSlot.BREAKFAST));

        mockMvc.perform(get(BASE_URL + "/" + medication.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ibuprofeno"))
                .andExpect(jsonPath("$.dose").value("200mg"))
                .andExpect(jsonPath("$.intakeSlots[0]").value("BREAKFAST"));
    }

    @Test
    @DisplayName("GET /medications/{id} - should return 404 if medication not found")
    void getMedicationById_WhenNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(containsString("Medication not found with id: 999")))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value(BASE_URL + "/999"));
    }

    @Test
    @DisplayName("GET /medications/user/{userId} - should return medications for a user")
    void getMedicationsByUser_ShouldReturnList() throws Exception {
        createMedication("Ibuprofeno", "Medicamento para el dolor", "200mg",
                LocalDate.now(), LocalDate.now().plusDays(7), List.of(IntakeSlot.BREAKFAST));
        createMedication("Paracetamol", "Para fiebre", "500mg",
                LocalDate.now(), LocalDate.now().plusDays(5), List.of(IntakeSlot.LUNCH));

        mockMvc.perform(get(BASE_URL + "/user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ibuprofeno"))
                .andExpect(jsonPath("$[1].name").value("Paracetamol"));
    }

    @Test
    @DisplayName("DELETE /medications/{id} - should delete medication successfully")
    void deleteMedication_ShouldReturnNoContent() throws Exception {
        Medication medication = createMedication("Ibuprofeno", "Medicamento para el dolor", "200mg",
                LocalDate.now(), LocalDate.now().plusDays(7), List.of(IntakeSlot.BREAKFAST));

        mockMvc.perform(delete(BASE_URL + "/" + medication.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /medications/{id} - should return 404 if medication not found")
    void deleteMedication_WhenNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(containsString("Medication not found")))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value(BASE_URL + "/999"));
    }

    private Medication createMedication(String name, String description, String dose,
                                        LocalDate startDate, LocalDate endDate, List<IntakeSlot> slots) {
        return medicationRepository.save(
                Medication.builder()
                        .name(name)
                        .description(description)
                        .dose(dose)
                        .user(testUser)
                        .startDate(startDate)
                        .endDate(endDate)
                        .intakeSlots(slots)
                        .build()
        );
    }

    private MedicationRequest createMedicationRequest(String name, String description, String dose,
                                                      LocalDate startDate, LocalDate endDate, List<IntakeSlot> slots) {
        return new MedicationRequest(name, description, dose, startDate, endDate, slots);
    }
}