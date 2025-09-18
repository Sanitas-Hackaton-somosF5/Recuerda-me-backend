package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDate;

import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeResponse(
                Long id,
                String medication_name,
                LocalDate date,
                IntakeSlot slot,
                Status status,
                String description,
                String dose) {
}
