package com.sanitas.recuerdame.user;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityTest {

    @Test
    void testAddMedicationsToUser() {
        User user = new User();
        user.setUsername("alexdev");
        user.setEmail("alex@example.com");
        user.setPassword("secure123");

        Medication med1 = Medication.builder()
                .name("Ibuprofeno")
                .description("Para dolor de cabeza")
                .dose("600mg")
                .startDate(LocalDate.of(2025, 9, 17))
                .endDate(LocalDate.of(2025, 9, 24))
                .intakeSlots(List.of(IntakeSlot.BREAKFAST, IntakeSlot.LUNCH))
                .build();

        Medication med2 = Medication.builder()
                .name("Paracetamol")
                .description("Para fiebre")
                .dose("500mg")
                .startDate(LocalDate.of(2025, 9, 17))
                .endDate(LocalDate.of(2025, 9, 20))
                .intakeSlots(List.of(IntakeSlot.DINNER))
                .build();


        user.setMedications(List.of(med1, med2));

        assertThat(user.getMedications()).hasSize(2);
        assertThat(user.getMedications()).extracting("name")
                .containsExactly("Ibuprofeno", "Paracetamol");
    }

    @Test
    void testMedicationsListCanBeModified() {
        User user = new User();
        user.setMedications(new java.util.ArrayList<>());

        Medication med = Medication.builder()
                .name("Amoxicilina")
                .description("Antibiótico")
                .dose("500mg")
                .startDate(LocalDate.of(2025, 9, 17))
                .endDate(LocalDate.of(2025, 9, 23))
                .intakeSlots(List.of(IntakeSlot.BREAKFAST))
                .build();

        user.getMedications().add(med);

        assertThat(user.getMedications()).hasSize(1);
        assertThat(user.getMedications().get(0).getName()).isEqualTo("Amoxicilina");
    }
}
