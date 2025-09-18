package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDate;

import com.sanitas.recuerdame.intake.Intake.StatusEnum;
import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeDTOResponse(
        Long id,
        String medicineName,
        LocalDate date,
        IntakeSlot slot,
        StatusEnum status,
        String description,
        String dose) {
}
