package com.sanitas.recuerdame.medications.dtos;

import com.sanitas.recuerdame.shared.IntakeSlot;

import java.time.LocalDate;
import java.util.List;

public record MedicationResponse(
        String name,
        String description,
        String dose,
        LocalDate startDate,
        LocalDate endDate,
        List<IntakeSlot> intakeSlots
) {
}
