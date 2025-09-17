package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDate;

import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeDTORequest(
    Long medication_id,
    LocalDate date,
    IntakeSlot slot) {
}