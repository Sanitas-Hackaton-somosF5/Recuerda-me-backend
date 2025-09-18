package com.sanitas.recuerdame.medications.dtos;

import com.sanitas.recuerdame.shared.IntakeSlot;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record MedicationRequest(

                @NotBlank(message = "El nombre del medicamento es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String name,

                @Size(min = 1, max = 1000, message = "La descripción no puede superar los 1000 caracteres") String description,

                @NotBlank(message = "La dosis es obligatoria") @Size(min = 1, max = 50, message = "La dosis no puede superar los 50 caracteres") String dose,

                @NotNull(message = "La fecha de inicio es obligatoria") LocalDate startDate,

                @NotNull(message = "La fecha de fin es obligatoria") @FutureOrPresent(message = "La fecha de fin debe ser hoy o una fecha futura") LocalDate endDate,

                @NotEmpty(message = "Debe seleccionar al menos una franja horaria") List<IntakeSlot> intakeSlots) {
}
