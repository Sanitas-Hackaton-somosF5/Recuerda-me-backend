package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDate;

import com.sanitas.recuerdame.intake.IntakeEntity.StatusEnum;
import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeDTOResponse(String medicineString, LocalDate day, IntakeSlot slot, StatusEnum status) {
}
