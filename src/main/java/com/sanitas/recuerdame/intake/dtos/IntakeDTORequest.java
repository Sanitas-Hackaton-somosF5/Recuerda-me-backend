package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDateTime;

import com.sanitas.recuerdame.shared.IntakeSlot;

public record IntakeDTORequest(Long medication_id, LocalDateTime dateTime, IntakeSlot slot) {
}