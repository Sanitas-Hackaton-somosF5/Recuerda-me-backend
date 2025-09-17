package com.sanitas.recuerdame.intake.service;

import java.util.List;

import com.sanitas.recuerdame.intake.dtos.IntakeDTORequest;
import com.sanitas.recuerdame.intake.dtos.IntakeDTOResponse;

public interface InterfaceIntakeService {

  IntakeDTOResponse createIntake(IntakeDTORequest request);

  List<IntakeDTOResponse> getAllIntakes();

  IntakeDTOResponse getIntakeById(Long id);

  void deleteIntake(Long id);
}
