package com.sanitas.recuerdame.intake.service;

import java.util.List;

import com.sanitas.recuerdame.intake.Intake;

public interface InterfaceIntakeService<T, S> {

  public T createIntake(S request);

  public List<T> getAllIntakes();

  public T getIntakeById(Long id);

  public void deleteIntake(Long id);

  public List<T> getTodayIntakes();

  public List<T> getIntakesByMedication(Long medicationId);

  T updateIntakeStatus(Long id, Intake.StatusEnum status);
}
