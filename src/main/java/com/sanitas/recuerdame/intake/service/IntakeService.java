package com.sanitas.recuerdame.intake.service;

import java.util.List;

import com.sanitas.recuerdame.intake.Status;

public interface IntakeService<T, S> {

  public T createIntake(S request);

  public List<T> getAllIntakes();

  public T getIntakeById(Long id);

  public void deleteIntake(Long id);

  public List<T> getTodayIntakes();

  public List<T> getIntakesByMedication(Long medicationId);

  T updateIntakeStatus(Long id, Status status);
}
