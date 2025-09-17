package com.sanitas.recuerdame.intake.mappers;

import org.springframework.stereotype.Component;

import com.sanitas.recuerdame.intake.IntakeEntity;
import com.sanitas.recuerdame.intake.IntakeEntity.StatusEnum;
import com.sanitas.recuerdame.intake.dtos.IntakeDTORequest;

@Component
public class IntakeMapper {

  public static IntakeEntity toEntity(IntakeDTORequest dtoRequest) {
    IntakeEntity intake = new IntakeEntity();
    intake.setStatus(StatusEnum.PENDING);

    return intake;
  };
}
