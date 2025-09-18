package com.sanitas.recuerdame.intake;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;

public class IntakeTest {
  @Test
  void testBuilderAndGetters() {
    Medication med = Medication.builder().id(1L).name("Ibuprofeno").build();

    Intake intake = Intake.builder()
        .id(100L)
        .date(LocalDate.of(2025, 9, 17))
        .slot(IntakeSlot.BREAKFAST)
        .status(Intake.StatusEnum.TAKEN)
        .medication(med)
        .build();

    assertThat(intake.getId()).isEqualTo(100L);
    assertThat(intake.getDate()).isEqualTo(LocalDate.of(2025, 9, 17));
    assertThat(intake.getSlot()).isEqualTo(IntakeSlot.BREAKFAST);
    assertThat(intake.getStatus()).isEqualTo(Intake.StatusEnum.TAKEN);
    assertThat(intake.getMedication()).isEqualTo(med);
  }

  @Test
  void testDefaultStatusIsPending() {
    Intake intake = Intake.builder()
        .date(LocalDate.now())
        .slot(IntakeSlot.LUNCH)
        .build();

    assertThat(intake.getStatus()).isEqualTo(Intake.StatusEnum.PENDING);
  }

  @Test
  void testEqualsAndHashCode() {
    Intake intake1 = Intake.builder().id(1L).build();
    Intake intake2 = Intake.builder().id(1L).build();

    assertThat(intake1).isEqualTo(intake2);
    assertThat(intake1.hashCode()).isEqualTo(intake2.hashCode());
  }
}
