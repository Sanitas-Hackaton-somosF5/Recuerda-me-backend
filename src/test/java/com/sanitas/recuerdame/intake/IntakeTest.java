package com.sanitas.recuerdame.intake;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class IntakeTest {
  @Test
  void testBuilderSetsScheduledTime() {
    LocalDateTime now = LocalDateTime.now();
    Intake intake = Intake.builder()
        .scheduledTime(now)
        .build();

    assertThat(intake.getScheduledTime()).isEqualTo(now);
  }

  @Test
  void testStatusDefaultIsPending() {
    Intake intake = Intake.builder()
        .scheduledTime(LocalDateTime.now())
        .build();

    assertThat(intake.getStatus()).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void testSettersAndGetters() {
    Intake intake = new Intake();
    LocalDateTime scheduledTime = LocalDateTime.of(2025, 9, 17, 10, 0);
    intake.setScheduledTime(scheduledTime);
    intake.setStatus(StatusEnum.TAKEN);

    assertThat(intake.getScheduledTime()).isEqualTo(scheduledTime);
    assertThat(intake.getStatus()).isEqualTo(StatusEnum.TAKEN);
  }

  @Test
  void testBuilderWithCustomStatus() {
    LocalDateTime now = LocalDateTime.now();
    Intake intake = Intake.builder()
        .scheduledTime(now)
        .status(StatusEnum.TAKEN)
        .build();

    assertThat(intake.getStatus()).isEqualTo(StatusEnum.TAKEN);
    assertThat(intake.getScheduledTime()).isEqualTo(now);
  }
}
