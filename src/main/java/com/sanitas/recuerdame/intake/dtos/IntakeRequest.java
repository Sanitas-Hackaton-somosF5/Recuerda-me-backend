package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDate;

import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeRequest(
        Long medicationId,
        LocalDate date,
        IntakeSlot slot) {
}