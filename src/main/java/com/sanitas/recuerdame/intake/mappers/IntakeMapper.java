package com.sanitas.recuerdame.intake.mappers;

import org.springframework.stereotype.Component;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.dtos.IntakeDTORequest;
import com.sanitas.recuerdame.intake.dtos.IntakeDTOResponse;
import com.sanitas.recuerdame.medications.Medication;

@Component
public class IntakeMapper {

  public static Intake toEntity(IntakeDTORequest dtoRequest, Medication medication) {
    return Intake.builder()
        .date(dtoRequest.date())
        .slot(dtoRequest.slot())
        .medication(medication)
        .build();
  };

  public static IntakeDTOResponse toDTO(Intake intake) {
    IntakeDTOResponse dtoResponse = new IntakeDTOResponse(
        intake.getId(),
        intake.getMedication().getName(),
        intake.getDate(),
        intake.getSlot(),
        intake.getStatus(),
        intake.getMedication().getDescription(),
        intake.getMedication().getDose());

    return dtoResponse;
  }
}
