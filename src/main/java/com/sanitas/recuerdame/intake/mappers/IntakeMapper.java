package com.sanitas.recuerdame.intake.mappers;

import org.springframework.stereotype.Component;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.medications.Medication;

@Component
public class IntakeMapper {

  public static Intake dtoToEntity(IntakeRequest dtoRequest, Medication medication) {
    return Intake.builder()
        .date(dtoRequest.date())
        .slot(dtoRequest.slot())
        .medication(medication)
        .build();
  }

  public static IntakeResponse entityToDTO(Intake intake) {
    IntakeResponse dtoResponse = new IntakeResponse(
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
