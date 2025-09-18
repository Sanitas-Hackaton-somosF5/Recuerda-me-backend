package com.sanitas.recuerdame.intake.service;

import java.util.List;

public interface IntakeService<T, S> {

  T createIntake(S request);

  List<T> getAllIntakes();

  T getIntakeById(Long id);

  void deleteIntake(Long id);
}
