package com.sanitas.recuerdame.intake.dtos;

import java.time.LocalDateTime;

public record IntakeDTORequest(Long medication_id, LocalDateTime dateTime) {
}