package com.sanitas.recuerdame.medications;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MedicationEntityTest {
    @Test
    void testMedicationBuilderAndGetters() {
        Medication medication = Medication.builder()
                .name("Ibuprofeno")
                .description("Para dolor de cabeza")
                .dose("600mg")
                .startDate(LocalDate.of(2025, 9, 17))
                .endDate(LocalDate.of(2025, 9, 24))
                .intakeSlots(List.of(IntakeSlot.BREAKFAST, IntakeSlot.LUNCH))
                .build();

        assertThat(medication.getName()).isEqualTo("Ibuprofeno");
        assertThat(medication.getDescription()).isEqualTo("Para dolor de cabeza");
        assertThat(medication.getDose()).isEqualTo("600mg");
        assertThat(medication.getStartDate()).isEqualTo(LocalDate.of(2025, 9, 17));
        assertThat(medication.getEndDate()).isEqualTo(LocalDate.of(2025, 9, 24));
        assertThat(medication.getIntakeSlots()).containsExactly(IntakeSlot.BREAKFAST, IntakeSlot.LUNCH);
    }

    @Test
    void testMedicationSetters() {
        Medication medication = new Medication();
        medication.setName("Paracetamol");
        medication.setDose("500mg");

        assertThat(medication.getName()).isEqualTo("Paracetamol");
        assertThat(medication.getDose()).isEqualTo("500mg");
    }
}
